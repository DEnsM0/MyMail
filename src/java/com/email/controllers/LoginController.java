package com.email.controllers;

import com.email.model.EmailAccount;
import com.email.services.EmailLoginStatus;
import com.email.services.LoginService;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
        System.out.println("loginButtonClicked");
        if(fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, getEmailManager());
            loginService.start();
            loginService.setOnSucceeded(e ->{
                EmailLoginStatus loginStatus = loginService.getValue();

                switch (loginStatus){
                    case SUCCESS -> {
                        if(getViewFactory().isMainInitialized()){
                            getViewFactory().showMain();
                        }
                        ViewFactory.closeStage(getStage());
                        break;
                    }
                    case FAILED_BY_CREDENTIALS -> {
                        errorLabel.setText("Wrong email or password.");
                        break;
                    }
                    case FAILED_BY_UNEXPECTED_ERROR -> {
                        errorLabel.setText("Unexpected error.");
                        break;
                    }
                    default -> {
                        break;
                    }
                }
            });
        }
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