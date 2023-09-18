package com.email.visuals;

import com.email.controllers.CommonController;
import com.email.controllers.LoginController;
import com.email.controllers.MainController;
import com.email.controllers.OptionsController;
import com.email.utils.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private final EmailManager emailManager;
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;
    private static ArrayList<Stage> activeStages;
    private boolean mainInitialized = false;

    public boolean isMainInitialized (){
        return mainInitialized;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<>();
    }

    private void buildStage(CommonController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxml()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = controller.getStage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public static void closeStage(Stage stage){
        stage.close();
        activeStages.remove(stage);
    }

    public void showLogin(){
        System.out.println("show login window");
        Stage stage = new Stage();
        buildStage(new LoginController(emailManager, this, "/com/email/view/login.fxml", stage));

    }

    public void showMain(){
        System.out.println("show main window");
        Stage stage = new Stage();
        buildStage(new MainController(emailManager, this, "/com/email/view/main.fxml", stage));
        mainInitialized = true;
    }

    public void showOptions(){
        System.out.println("show options window");
        Stage stage = new Stage();
        buildStage(new OptionsController(emailManager, this, "/com/email/view/options-panel.fxml", stage));
    }

    public void updateView() {
        for (Stage stage : activeStages){
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());


        }
    }
}
