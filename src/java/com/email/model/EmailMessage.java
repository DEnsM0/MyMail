package com.email.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.*;

public class EmailMessage {
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<EmailSize> size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;
    private Set<MimeBodyPart> attachments = new HashSet<>();

    public EmailMessage(String subject, String sender, String recipient, long size, Date date, boolean isRead, Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<>(new EmailSize(size));
        this.date = new SimpleObjectProperty<>(date);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject() {
        return subject.get();
    }

    public String getSender() {
        return sender.get();
    }

    public String getRecipient() {
        return recipient.get();
    }

    public EmailSize getSize() {
        return size.get();
    }

    public Date getDate() {
        return date.get();
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Message getMessage() {
        return message;
    }

    public void addAttachment(MimeBodyPart mimeBodyPart) {
        attachments.add(mimeBodyPart);
    }

    public boolean hasAttachments(){
        return !attachments.isEmpty();
    }

    public Set<MimeBodyPart> getAttachments() {
        return attachments;
    }
}
