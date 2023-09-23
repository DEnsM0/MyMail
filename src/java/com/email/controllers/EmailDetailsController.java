package com.email.controllers;

import com.email.model.AttachmentButton;
import com.email.model.EmailMessage;
import com.email.services.MessageRenderService;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends CommonController implements Initializable {
    private static final String DOWNLOADS_PATH = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private HBox attachmentsHBox;

    @FXML
    private Label attachmentsLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;
    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmailMessage emailMassage = getEmailManager().getSelectedMessage();
        subjectLabel.setText(emailMassage.getSubject());
        senderLabel.setText(emailMassage.getSender());
        
        loadAttachments(emailMassage);
        
        MessageRenderService messageRenderService = new MessageRenderService(webView.getEngine());
        messageRenderService.setEmailMessage(emailMassage);
        messageRenderService.restart();
    }

    private void loadAttachments(EmailMessage emailMassage) {
        if(emailMassage.hasAttachments()){
            for (MimeBodyPart attachment: emailMassage.getAttachments()){
                try {
                    AttachmentButton button = new AttachmentButton(DOWNLOADS_PATH, attachment);
                    attachmentsHBox.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } else{
            attachmentsLabel.setText("");
        }
    }
}
