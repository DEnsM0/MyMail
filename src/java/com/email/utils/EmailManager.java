package com.email.utils;

import com.email.model.EmailAccount;
import javafx.scene.control.TreeItem;

public class EmailManager {
    TreeItem<String> foldersRoot = new TreeItem<>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addAccount(EmailAccount emailAccount){
        TreeItem<String> treeItem = new TreeItem<>(emailAccount.getAddress());
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }
}
