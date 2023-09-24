package com.email.model;

import com.email.visuals.IconResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class AttachmentButton extends Button {
    private static final String DEFAULT_STYLE = """
                    -fx-background-color: transparent;
                    -fx-border-color: lightgray;
                    -fx-border-radius: 0;
                        -fx-border-width: 1px;
                        -fx-padding: 5px;
                        -fx-cursor: hand;""";
    private String downloadedFilePath;
    private MimeBodyPart mimeBodyPart;

    private IconResolver iconResolver = new IconResolver();

    public AttachmentButton(String downloadsPath, MimeBodyPart mimeBodyPart) throws MessagingException {
        this.downloadedFilePath = downloadsPath + mimeBodyPart.getFileName();
        this.mimeBodyPart = mimeBodyPart;
        this.setText(mimeBodyPart.getFileName());
        this.setStyle( DEFAULT_STYLE);
        this.setGraphic(iconResolver.getAttachmentIcon(mimeBodyPart.getFileName()));
        if(new File(downloadedFilePath).exists()){
            colorGreen();
        }
        this.setOnAction(e -> interactWithAttachment());
    }

    private void interactWithAttachment() {
        if(!new File(downloadedFilePath).exists()){
            downloadFile();
        } else{
            openFile();
        }

    }

    private void openFile() {
    new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        openDownloadedFile();
                        return null;
                    }
                };
            }
        }.restart();
    }

    private void downloadFile() {
        colorBlue();
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        mimeBodyPart.saveFile(downloadedFilePath);
                        return null;
                    }
                };
            }
        };
        service.restart();
        service.setOnSucceeded(e -> {
            colorGreen();
            this.setOnAction(e1 -> {
                openDownloadedFile();
            });
        });
    }

    private void openDownloadedFile() {
        File file = new File(downloadedFilePath);
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()){
            try {
                desktop.open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void colorBlue(){
        this.setStyle(DEFAULT_STYLE + "-fx-background-color: #4E91FD");
    }

    private void colorGreen(){
        this.setStyle(DEFAULT_STYLE + "-fx-background-color: #a2d895");
    }

    
}
