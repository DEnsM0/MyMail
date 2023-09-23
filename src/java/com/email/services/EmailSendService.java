package com.email.services;

import com.email.model.EmailAccount;
import com.email.utils.EmailSendStatus;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

public class EmailSendService extends Service<EmailSendStatus> {
    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;
    private List<File> attachments;

    public EmailSendService(EmailAccount emailAccount, String subject, String recipient, String content, List<File> attachments) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
        this.attachments = attachments;
    }

    @Override
    protected Task<EmailSendStatus> createTask() {
        return new Task<EmailSendStatus>() {
            @Override
            protected EmailSendStatus call() {
                try {
                    MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                    mimeMessage.setFrom(emailAccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);

                    Multipart multipart = new MimeMultipart();
                    BodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setContent(content,"text/html");
                    multipart.addBodyPart(bodyPart);
                    mimeMessage.setContent(multipart);

                    if(!attachments.isEmpty()){
                        for(File file : attachments){
                            MimeBodyPart mimeBodyPart = new MimeBodyPart();
                            mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(file.getAbsolutePath())));
                            mimeBodyPart.setFileName(file.getName());
                            multipart.addBodyPart(mimeBodyPart);
                        }
                    }

                    Transport transport = emailAccount.getSession().getTransport();
                    transport.connect(
                            emailAccount.getProperties().getProperty("outgoingHost"),
                            emailAccount.getAddress(),
                            emailAccount.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSendStatus.SUCCESS;
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return EmailSendStatus.FAILED_BY_PROVIDER;
                } catch (Exception e) {
                    e.printStackTrace();
                    return EmailSendStatus.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }
}
