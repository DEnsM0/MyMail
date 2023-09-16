package com.email.controllers;

import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.stage.Stage;

public abstract class CommonController {
    private EmailManager emailManager;
    private ViewFactory viewFactory;
    private String fxml;
    private Stage stage;

    public CommonController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxml = fxml;
        this.stage = stage;
    }

    public EmailManager getEmailManager() {
        return emailManager;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public String getFxml() {
        return fxml;
    }

    public Stage getStage() {
        return stage;
    }
}
