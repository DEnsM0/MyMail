package com.email.services;

import com.email.model.EmailAccount;
import com.email.utils.EmailSendStatus;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailSendService extends Service<EmailSendStatus> {
    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;

    public EmailSendService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    @Override
    protected Task<EmailSendStatus> createTask() {
        return new Task<EmailSendStatus>() {
            @Override
            protected EmailSendStatus call() throws Exception {
                return null;
            }
        };
    }
}
