package com.email.controllers;

import com.email.model.EmailAccount;
import com.email.services.EmailLoginStatus;
import com.email.services.LoginService;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends CommonController {

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    public LoginController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @FXML
    void loginButtonClicked() {
        if(fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, getEmailManager());

            EmailLoginStatus loginStatus = loginService.login();

            switch (loginStatus){
                case SUCCESS -> {
                    System.out.println("logged in");
                    return;
                }
            }
        }
        System.out.println("loginButtonClicked");
        getViewFactory().showMain();
        ViewFactory.closeStage(getStage());
    }

    private boolean fieldsAreValid() {
        if(emailField.getText().isEmpty()){
            errorLabel.setText("Address field is empty");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Password field is empty");
            return false;
        }
        return true;
    }


}