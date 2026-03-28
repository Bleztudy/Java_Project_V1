

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bibliotheque.fxml"));
        Scene scene = new Scene(loader.load());
        
        stage.setTitle("Gestion de Bibliothèque");
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(750);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}