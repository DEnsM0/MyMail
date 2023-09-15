package com.email.controllers;


import com.email.CommonController;
import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainController extends CommonController {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private TreeView<?> emailsTreeView;

    public MainController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void openOptions() {

    }

}