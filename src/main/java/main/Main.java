package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Charger l'icône personnalisée
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);
        
        SceneManager.setPrimaryStage(stage);
        stage.setWidth(1128);
        stage.setHeight(812);
        stage.setMinWidth(1128);
        stage.setMinHeight(812);
        stage.setMaxWidth(1128);
        stage.setMaxHeight(812);
        SceneManager.showAccueil();
    }

    public static void main(String[] args) {
        launch(args);
    }
}