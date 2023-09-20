package com.email.services;

import com.email.model.EmailAccount;
import com.email.utils.EmailLoginStatus;
import com.email.utils.EmailManager;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;

public class LoginService extends Service<EmailLoginStatus> {
    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginStatus login(){
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };
        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            emailAccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                    emailAccount.getAddress(),
                    emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addAccount(emailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginStatus.FAILED_BY_NETWORK;
        } catch (AuthenticationFailedException e){
            e.printStackTrace();
            return EmailLoginStatus.FAILED_BY_CREDENTIALS;
        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginStatus.FAILED_BY_UNEXPECTED_ERROR;
        } catch (Exception e){
            e.printStackTrace();
            return EmailLoginStatus.FAILED_BY_UNEXPECTED_ERROR;
        }

        return EmailLoginStatus.SUCCESS;
    }

    @Override
    protected Task<EmailLoginStatus> createTask() {
        return new Task<EmailLoginStatus>() {
            @Override
            protected EmailLoginStatus call() throws Exception {
                return login();
            }
        };
    }
}
