package com.email.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class AttachmentButton extends Button {
    private String downloadedFilePath;
    private MimeBodyPart mimeBodyPart;

    public AttachmentButton(String downloadsPath, MimeBodyPart mimeBodyPart) throws MessagingException {
        this.downloadedFilePath = downloadsPath + mimeBodyPart.getFileName();
        this.mimeBodyPart = mimeBodyPart;
        this.setText(mimeBodyPart.getFileName());
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
        this.setStyle("-fx-background-color: #4E91FD");
    }

    private void colorGreen(){
        this.setStyle("-fx-background-color: #a2d895");
    }

    
}
