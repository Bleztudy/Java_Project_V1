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
    
    private final List<String> carouselImages = Arrays.asList(
        "/images/carousel/slide-1.jpg",
        "/images/carousel/slide-2.jpg",
        "/images/carousel/slide-3.jpg",
        "/images/carousel/slide-4.jpg",
        "/images/carousel/slide-5.jpg",
        "/images/carousel/slide-6.jpg"
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
        appliquerImageCarrousel(0);
        
        carouselTimeline = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> {
                currentIndex = (currentIndex + 1) % carouselImages.size();
                appliquerImageCarrousel(currentIndex);
            })
        );
        carouselTimeline.setCycleCount(Timeline.INDEFINITE);
        carouselTimeline.play();
    }
    
    private void appliquerImageCarrousel(int index) {
        try {
            String imagePath = getClass().getResource(carouselImages.get(index)).toExternalForm();
            carouselPane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover; -fx-background-position: center;");
        } catch (Exception e) {
            // Fallback à une couleur si l'image n'existe pas
            carouselPane.setStyle("-fx-background-color: #667eea;");
        }
        
        FadeTransition fade = new FadeTransition(Duration.seconds(1), carouselPane);
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