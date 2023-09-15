package com.email;

import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;

public abstract class CommonController {
    private EmailManager emailManager;
    private ViewFactory viewFactory;
    private String fxmlName;

    public CommonController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
}
