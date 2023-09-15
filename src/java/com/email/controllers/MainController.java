package com.email.controllers;


import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

import javafx.stage.Stage;

public class MainController extends CommonController {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private TreeView<?> emailsTreeView;

    public MainController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @FXML
    void openOptions() {
        getViewFactory().showOptions();
    }

}