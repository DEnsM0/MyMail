package com.email.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {
    private String name;
    private ObservableList<EmailMessage> emailMessages;

    private int unreadCounter;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmailMessages() {
        return emailMessages;
    }

    public void addEmail(Message message) throws MessagingException {
        boolean messageRead = message.getFlags().contains(Flags.Flag.SEEN);
        emailMessages.add(new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageRead,
                message
        ));
        if(!messageRead){
            incrementUnread();
        }
        System.out.println("added to" + name + message.getSubject());
    }

    private void updateName(){
        this.setValue((String)
                (unreadCounter > 0 ?
                java.lang.String.format("%s(%d)", name, unreadCounter) :
                name));
    }

    public void incrementUnread(){
        unreadCounter++;
        updateName();
    }
}
