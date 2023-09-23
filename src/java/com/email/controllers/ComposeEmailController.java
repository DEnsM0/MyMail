package com.email.controllers;

import com.email.model.EmailAccount;
import com.email.services.EmailSendService;
import com.email.utils.EmailManager;
import com.email.utils.EmailSendStatus;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ComposeEmailController extends CommonController implements Initializable {
    List<File> attachedFiles = new ArrayList<>();
    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    void sendButtonAction() {
        EmailSendService emailSendService = new EmailSendService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText(),
                attachedFiles
        );
        emailSendService.start();
        emailSendService.setOnSucceeded(e -> {
            EmailSendStatus emailSendStatus = emailSendService.getValue();
            switch (emailSendStatus) {
                case SUCCESS -> {
                   ViewFactory.closeStage(getStage());
                    break;
                }
                case FAILED_BY_PROVIDER -> {
                    errorLabel.setText("Provider error!");
                    break;
                }
                case FAILED_BY_UNEXPECTED_ERROR -> {
                    errorLabel.setText("Unexpected error!");
                    break;
                }
            }
        });
    }

    @FXML
    void attachButtonAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            attachedFiles.add(file);
        }
    }

    public ComposeEmailController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAccountChoice.setItems(getEmailManager().getEmailAccounts());
        emailAccountChoice.setValue(getEmailManager().getEmailAccounts().get(0));
    }
}
