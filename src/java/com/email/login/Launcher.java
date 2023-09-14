package com.email.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/email/view/login-view.fxml"));


        Scene scene = new Scene(parent, 516, 400);

        stage.setScene(scene);

        stage.show();
    }
}
