package com.email.utils;

import com.email.model.EmailAccount;
import com.email.model.EmailTreeItem;
import com.email.services.FetchFolderService;
import com.email.services.UpdateFolderService;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private UpdateFolderService updateFolderService;
    /**
     * Folder handling.
     */
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<>();

    public List<Folder> getFolderList() {
        return folderList;
    }

    public EmailManager(){
        updateFolderService = new UpdateFolderService(folderList);
        updateFolderService.start();
    }

    public void addAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
