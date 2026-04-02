package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
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