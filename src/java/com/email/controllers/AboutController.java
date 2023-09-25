package com.email.controllers;

import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutController extends CommonController{

    @FXML
    private Label authorLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private VBox vBox;

    @FXML
    private Label versionLabel;

    public AboutController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }
}
