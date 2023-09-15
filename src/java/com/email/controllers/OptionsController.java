package com.email.controllers;

import com.email.utils.EmailManager;
import com.email.utils.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class OptionsController extends CommonController{

    @FXML
    private ChoiceBox<?> colorThemeChoiceBox;

    @FXML
    private Slider fontSizeSlider;

    public OptionsController(EmailManager emailManager, ViewFactory viewFactory, String fxml, Stage stage) {
        super(emailManager, viewFactory, fxml, stage);
    }

    @FXML
    void applyButtonClicked() {

    }

    @FXML
    void cancelButtonClicked() {

    }

}