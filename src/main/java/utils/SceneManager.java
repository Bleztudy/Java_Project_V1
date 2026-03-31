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
    
    private static void loadScene(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(SceneManager.class.getResource("/view/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setWidth(1128);
        primaryStage.setHeight(812);
        
        // Ajouter une transition de fondu
        scene.setFill(null);
    }
    
    public static void showAccueil() throws IOException {
        loadScene("/view/accueil.fxml", "BiblioTech - Accueil");
    }
    
    public static void showCatalogue() throws IOException {
        loadScene("/view/catalogue.fxml", "BiblioTech - Catalogue");
    }
    
    public static void showEmprunt() throws IOException {
        loadScene("/view/emprunt.fxml", "BiblioTech - Emprunts");
    }
    
    public static void showBibliotheque() throws IOException {
        loadScene("/view/bibliotheque.fxml", "BiblioTech - Administration");
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void showAdminLogin() throws IOException {
    loadScene("/view/admin_login.fxml", "BiblioTech - Administration");
}

    public static void showAdminDashboard() throws IOException {
        loadScene("/view/admin_dashboard.fxml", "BiblioTech - Administration");
}
}