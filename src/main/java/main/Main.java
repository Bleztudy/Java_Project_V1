package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icône non trouvée, utilisation de l'icône par défaut");
        }
        
        SceneManager.setPrimaryStage(stage);
        stage.setTitle("BiblioTech");
        stage.setWidth(900);
        stage.setHeight(600);
        
        SceneManager.showAccueil();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}