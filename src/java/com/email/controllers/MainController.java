package com.email.controllers;


import com.email.model.EmailMessage;
import com.email.model.EmailSize;
import com.email.model.EmailTreeItem;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends CommonController implements Initializable {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableColumn<EmailMessage, String> recipientColumn;

    @FXML
    private TableColumn<EmailMessage, String> senderColumn;

    @FXML
    private TableColumn<EmailMessage, EmailSize> sizeColumn;

    @FXML
    private TableColumn<EmailMessage, String> subjectColumn;

    @FXML
    private TableColumn<EmailMessage, Date> dateColumn;

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
        setUpTableView();
        setUpFolderSelection();
        setUpBoldRows();
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(param -> new TableRow<>(){
            @Override
            protected void updateItem(EmailMessage item, boolean empty){
                super.updateItem(item, empty);
                if(item != null){
                    if(item.isRead()){
                        setStyle("");
                    } else {
                        setStyle("-fx-font-weight: bold");
                    }
                }
            }
        });
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if(item != null){
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpTableView() {
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        recipientColumn.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(getEmailManager().getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }

}