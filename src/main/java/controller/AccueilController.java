package controller;

import dao.EmpruntDAO;
import dao.EtudiantDAO;
import dao.LivreDAO;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.SceneManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AccueilController {
    
    @FXML
    private VBox rootPane;
    
    @FXML
    private StackPane carouselPane;
    
    @FXML
    private Text statLivres;
    
    @FXML
    private Text statEtudiants;
    
    @FXML
    private Text statEmprunts;
    
    private LivreDAO livreDAO;
    private EtudiantDAO etudiantDAO;
    private EmpruntDAO empruntDAO;
    private int currentIndex = 0;
    private Timeline carouselTimeline;
    
    private final List<String> carouselColors = Arrays.asList(
        "#667eea",
        "#f093fb",
        "#4facfe",
        "#43e97b"
    );
    
    @FXML
    public void initialize() {
        livreDAO = new LivreDAO();
        etudiantDAO = new EtudiantDAO();
        empruntDAO = new EmpruntDAO();
        
        chargerStatistiques();
        demarrerCarrousel();
    }
    
    private void chargerStatistiques() {
        try {
            statLivres.setText(String.valueOf(livreDAO.getAllLivres().size()));
            statEtudiants.setText(String.valueOf(etudiantDAO.getAllEtudiants().size()));
            statEmprunts.setText(String.valueOf(empruntDAO.getEmpruntsEnCours().size()));
        } catch (Exception e) {
            statLivres.setText("0");
            statEtudiants.setText("0");
            statEmprunts.setText("0");
        }
    }
    
    private void demarrerCarrousel() {
        appliquerCarrouselItem(0);
        
        carouselTimeline = new Timeline(
            new KeyFrame(Duration.seconds(4), event -> {
                currentIndex = (currentIndex + 1) % carouselColors.size();
                appliquerCarrouselItem(currentIndex);
            })
        );
        carouselTimeline.setCycleCount(Timeline.INDEFINITE);
        carouselTimeline.play();
    }
    
    private void appliquerCarrouselItem(int index) {
        carouselPane.setStyle("-fx-background-color: " + carouselColors.get(index) + ";");
        
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), carouselPane);
        fade.setFromValue(0.7);
        fade.setToValue(1);
        fade.play();
    }
    
    @FXML
    private void goToAccueil() throws IOException {
        SceneManager.showAccueil();
    }
    
    @FXML
    private void goToCatalogue() throws IOException {
        SceneManager.showCatalogue();
    }
    
    @FXML
    private void goToAdmin() throws IOException {
        SceneManager.showAdminLogin();
    }
}