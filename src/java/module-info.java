module Email.App {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens com.email;
    opens com.email.controllers;
    opens com.email.model;
    opens com.email.visuals;
}