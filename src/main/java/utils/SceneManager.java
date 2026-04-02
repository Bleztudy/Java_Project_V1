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
        primaryStage.setTitle("BiblioTech - Accueil");
    }
    
    public static void showCatalogue() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/catalogue.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("BiblioTech - Catalogue");
    }
    
    public static void showEmprunt() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/emprunt.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("BiblioTech - Emprunts");
    }
    
    public static void showAdminLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/admin_login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("BiblioTech - Connexion Admin");
    }
    
    public static void showAdminDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/view/admin_dashboard.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("BiblioTech - Tableau de bord");
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}