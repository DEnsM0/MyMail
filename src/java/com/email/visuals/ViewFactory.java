package com.email.visuals;

import com.email.controllers.*;
import com.email.utils.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ViewFactory {

    private final EmailManager emailManager;
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;
    private static Set<Stage> activeStages;
    private static Set<CommonController> activeControllers;
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

    public static Set<Stage> getActiveStages() {
        return activeStages;
    }

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new HashSet<>();
        activeControllers = new HashSet<>();
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
        updateView(scene);
        Stage stage = controller.getStage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/email/view/icons/icon.png")));
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
        activeControllers.add(controller);
    }

    public static void closeStage(Stage stage){
        stage.close();
        activeStages.remove(stage);
    }

    public void showLogin(){
        if(!showExisting(LoginController.class)){
            Stage stage = new Stage();
            stage.setTitle("MyMail - Login");
            stage.setResizable(false);
            buildStage(new LoginController(emailManager, this, "/com/email/view/login.fxml", stage));
        }
    }

    public void showMain(){
        if(!showExisting(MainController.class)){
            Stage stage = new Stage();
            stage.setTitle("MyMail");
            buildStage(new MainController(emailManager, this, "/com/email/view/main.fxml", stage));
            mainInitialized = true;
        }
    }

    public void showOptions(){
        if(!showExisting(OptionsController.class)){
            Stage stage = new Stage();
            stage.setTitle("MyMail - Options");
            stage.setResizable(false);
            buildStage(new OptionsController(emailManager, this, "/com/email/view/options-panel.fxml", stage));
        }
    }

    public void showComposeEmail(){
        if(!showExisting(ComposeEmailController.class)){
            Stage stage = new Stage();
            stage.setTitle("MyMail - Compose");
            buildStage(new ComposeEmailController(emailManager, this, "/com/email/view/compose-email.fxml", stage));
        }
    }

    public void showEmailDetails(){
        Stage stage = new Stage();
        stage.setTitle("MyMail - Details");
        buildStage(new EmailDetailsController(emailManager, this, "/com/email/view/email-details.fxml", stage));
    }

    public void showAbout(){
        if(!showExisting(AboutController.class)){
            Stage stage = new Stage();
            stage.setTitle("MyMail - About");
            stage.setResizable(false);
            buildStage(new AboutController(emailManager, this, "/com/email/view/about.fxml", stage));
        }
    }

    public void updateAllViews() {
        for (Stage stage : activeStages){
            Scene scene = stage.getScene();
            updateView(scene);
        }
    }

    private void updateView(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
    }

    private <T> T findControllerByType(Class<T> controllerClass) {
        return activeControllers.stream()
                .filter(c -> c.getStage().isShowing())
                .filter(controllerClass::isInstance)
                .map(controllerClass::cast)
                .findFirst()
                .orElse(null);
    }

    private <T extends CommonController> boolean showExisting(Class<T> controllerClass) {
        T controller = findControllerByType(controllerClass);
        if (controller != null) {
            Stage stage = controller.getStage();
            if (stage != null) {
                stage.setIconified(false);
                stage.requestFocus();
                stage.toFront();
                stage.show();
                return true;
            }
        }
        return false;
    }
}
