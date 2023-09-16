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

public class ViewFactory {

    private final EmailManager emailManager;
    private ColorTheme colorTheme = ColorTheme.DEFAULT;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    private FontSize fontSize = FontSize.MEDIUM;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
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
    }

    public static void closeStage(Stage stage){
        stage.close();
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
    }

    public void showOptions(){
        System.out.println("show options window");
        Stage stage = new Stage();
        buildStage(new OptionsController(emailManager, this, "/com/email/view/options-panel.fxml", stage));
    }
}
