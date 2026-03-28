package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;
    
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    
    public static void showAccueil() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/accueil.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bibliothèque - Accueil");
        primaryStage.show();
    }
    
    public static void showCatalogue() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/catalogue.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bibliothèque - Catalogue");
        primaryStage.show();
    }
    
    public static void showEmprunt() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/emprunt.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bibliothèque - Emprunter");
        primaryStage.show();
    }
}