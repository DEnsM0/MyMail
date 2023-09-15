package com.email.controllers;

import com.email.CommonController;
import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends CommonController {

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    public LoginController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonClicked() {
        System.out.println("Clicked!-");
    }

}