package com.email.controllers;

import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;
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
        System.out.println("loginButtonClicked");
        getViewFactory().showMain();
        ViewFactory.closeStage(getStage());
    }

}