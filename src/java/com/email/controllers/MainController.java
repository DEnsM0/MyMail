package com.email.controllers;


import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends CommonController implements Initializable {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private TreeView<String> emailsTreeView;

    public MainController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @FXML
    void openOptions() {
        getViewFactory().showOptions();
    }

    @FXML
    void addAccountAction() {
        getViewFactory().showLogin();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(getEmailManager().getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }
}