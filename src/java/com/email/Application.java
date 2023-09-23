package com.email;

import com.email.utils.EmailManager;
import com.email.visuals.ViewFactory;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLogin();
    }
}
