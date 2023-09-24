package com.email;

import com.email.model.EmailAccount;
import com.email.persistence.LoginData;
import com.email.persistence.PersistenceAccess;
import com.email.services.LoginService;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Application extends javafx.application.Application {
    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<LoginData> loginDataList = persistenceAccess.loadFromPersistence();
        if(loginDataList.isEmpty()){
           viewFactory.showLogin();
        } else {
            viewFactory.showMain();
            loginDataList.stream()
                    .map(loginData -> new EmailAccount(loginData.getEmail(), loginData.getPassword()))
                    .map(emailAccount -> new LoginService(emailAccount, emailManager))
                    .forEach(LoginService::start);
        }
    }

    @Override
    public void stop() throws Exception {
        List<LoginData> loginDataList = emailManager.getEmailAccounts()
                .stream()
                .map(emailAccount -> new LoginData(emailAccount.getAddress(), emailAccount.getPassword()))
                .toList();
        persistenceAccess.saveToPersistence(loginDataList);
    }
}
