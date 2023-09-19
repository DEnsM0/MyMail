package com.email.services;

import com.email.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRenderService extends Service {
    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public MessageRenderService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(e -> {
            displayMassage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    loadMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

            }
        };
    }

    private void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0);
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimple(contentType)){
            stringBuffer.append(message.getContent().toString());
        } else if (isMultipart(contentType)) {
            Multipart multipart = (Multipart) message.getContent();
            for(int i = multipart.getCount()-1; i>=0; i--){
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPArtContentType = bodyPart.getContentType();
                if (isSimple(bodyPArtContentType)) {
                    stringBuffer.append(bodyPart.getContent().toString());
                }
            }
        }
    }

    private boolean isSimple(String contentType){
        return contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed") ||
                contentType.contains("text");
    }

    private boolean isMultipart(String contentType){
        return contentType.contains("multipart");
    }

    private void displayMassage(){
        webEngine.loadContent(stringBuffer.toString());
    }
}
