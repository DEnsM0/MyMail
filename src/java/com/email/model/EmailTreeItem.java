package com.email.model;

import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {
    public String name;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
    }
}
