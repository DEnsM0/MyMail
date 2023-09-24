package com.email.controllers;

import com.email.model.EmailAccount;
import com.email.model.EmailSize;
import com.email.services.EmailSendService;
import com.email.utils.EmailManager;
import com.email.utils.EmailSendStatus;
import com.email.visuals.IconResolver;
import com.email.visuals.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComposeEmailController extends CommonController implements Initializable {
    private static final long MAX_FILE_SIZE = 25 * 1024 * 1024;
    private long attachmentsSize = 0;
    private IconResolver iconResolver = new IconResolver();
    private List<File> attachedFiles = new ArrayList<>();
    @FXML
    private HBox attachmentsHBox;
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
            if(file.length() + attachmentsSize>MAX_FILE_SIZE){
                errorLabel.setText("File size limit 25 MB!" +
                        (attachmentsSize > 0 ? " Current size: " + new EmailSize(attachmentsSize) : ""));
                return;
            }
            attachedFiles.add(file);
            attachmentsSize += file.length();
            if(attachedFiles.size()<2){
                htmlEditor.setPrefHeight(htmlEditor.getPrefHeight() - 35);
            }
            AttachmentLabel label = new AttachmentLabel(file.getName(),file);
            label.setGraphic(iconResolver.getAttachmentIcon(file.getName()));
            HBox.setMargin(label, new Insets(0, 5,0,0));
            attachmentsHBox.getChildren().add(label);
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

    class AttachmentLabel extends Label {
        File selectedFile;
        MenuItem viewItem = new MenuItem("View");
        MenuItem removeItem = new MenuItem("Remove");
        public AttachmentLabel(String text, File selectedFile) {
            super(text);
            this.selectedFile = selectedFile;
            this.setStyle("""
                    -fx-border-color: lightgray;
                        -fx-border-width: 1px;
                        -fx-padding: 5px;
                        -fx-cursor: hand;""");
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(viewItem, removeItem);
            this.setContextMenu(contextMenu);
            setUpContextMenus();
            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(this, event.getScreenX(), event.getScreenY());
                } else if (event.getButton() == MouseButton.PRIMARY) {
                    CompletableFuture.runAsync(this::viewFile);
                }
            });
        }
        private void setUpContextMenus() {
            viewItem.setOnAction(e -> {
                viewFile();
            });
            removeItem.setOnAction(e -> {
                attachedFiles.remove(selectedFile);
                attachmentsHBox.getChildren().remove(this);
                if(attachedFiles.isEmpty()){
                    htmlEditor.setPrefHeight(htmlEditor.getPrefHeight() + 35);
                }
            });
        }
        private void viewFile(){
            Desktop desktop = Desktop.getDesktop();
            if(selectedFile.exists()){
                try {
                    desktop.open(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
