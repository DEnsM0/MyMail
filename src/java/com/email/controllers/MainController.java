package com.email.controllers;


import com.email.model.EmailMessage;
import com.email.model.EmailSize;
import com.email.model.EmailTreeItem;
import com.email.services.MessageRenderService;
import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends CommonController implements Initializable {
    private MenuItem markUnread = new MenuItem("Mark unread");
    private MenuItem deleteMessage = new MenuItem("Delete message");
    private MenuItem showEmailDetails = new MenuItem("View details");
    private MessageRenderService messageRenderService;
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
    @FXML
    void openOptions() {
        getViewFactory().showOptions();
    }
    @FXML
    void addAccountAction() {
        getViewFactory().showLogin();
    }
    @FXML
    void composeEmailAction() {
        getViewFactory().showComposeEmail();
    }
    @FXML
    void openAbout() {
        getViewFactory().showAbout();
    }
    @FXML
    void exitAction() {
        List<Stage> stagesToClose = new ArrayList<>(ViewFactory.getActiveStages());
        stagesToClose.stream().forEach(ViewFactory::closeStage);
    }

    public MainController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
        setUpTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRenderService();
        setUpMessageSelection();
        setUpContextMenus();
    }

    private void setUpContextMenus() {
        markUnread.setOnAction(e -> {
            getEmailManager().setUnread();
        });
        deleteMessage.setOnAction(e -> {
            getEmailManager().deleteSelectedMessage();
            emailWebView.getEngine().loadContent("");
        });
        showEmailDetails.setOnAction(e -> {
            getViewFactory().showEmailDetails();
        });
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(e -> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null){
                getEmailManager().setSelectedMessage(emailMessage);
                if(!emailMessage.isRead()){
                    getEmailManager().setRead();
                }
                messageRenderService.setEmailMessage(emailMessage);
                messageRenderService.restart();
            }
        });
    }

    private void setUpMessageRenderService() {
        messageRenderService = new MessageRenderService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(param -> new TableRow<>(){
            @Override
            protected void updateItem(EmailMessage item, boolean empty){
                super.updateItem(item, empty);
                if(item != null){
                    setStyle(item.isRead() ? "" : "-fx-font-weight: bold");
                }
            }
        });
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if(item != null){
                getEmailManager().setSelectedFolder(item);
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
        emailsTableView.setContextMenu(new ContextMenu(markUnread,deleteMessage,showEmailDetails));
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(getEmailManager().getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }

}