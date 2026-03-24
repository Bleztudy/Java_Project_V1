package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bibliotheque.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Gestion Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Pour VSCode, ajoute ça pour le lancement direct
    static {
        // Configure JavaFX modules pour VSCode
        try {
            Class.forName("javafx.application.Application");
        } catch (ClassNotFoundException e) {
            System.err.println("JavaFX not found. Run with: mvn javafx:run");
        }
    }
}