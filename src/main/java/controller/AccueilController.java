package controller;

import dao.EtudiantDAO;
import dao.LivreDAO;
import dao.EmpruntDAO;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    
    @FXML
    private ToggleButton themeToggle;
    
    private LivreDAO livreDAO;
    private EtudiantDAO etudiantDAO;
    private EmpruntDAO empruntDAO;
    private int currentImageIndex = 0;
    private Timeline carouselTimeline;
    
    private final List<String> carouselImages = Arrays.asList(
        "https://picsum.photos/id/20/1920/1080",  // Bibliothèque
        "https://picsum.photos/id/24/1920/1080",  // Livres
        "https://picsum.photos/id/26/1920/1080"   // Lecture
    );
    
    @FXML
    public void initialize() {
        livreDAO = new LivreDAO();
        etudiantDAO = new EtudiantDAO();
        empruntDAO = new EmpruntDAO();
        
        chargerStatistiques();
        demarrerCarrousel();
        appliquerThemeClair();
        
        themeToggle.setText("🌙");
        themeToggle.setOnAction(e -> toggleTheme());
    }
    
    private void chargerStatistiques() {
        statLivres.setText(String.valueOf(livreDAO.getAllLivres().size()));
        statEtudiants.setText(String.valueOf(etudiantDAO.getAllEtudiants().size()));
        statEmprunts.setText(String.valueOf(empruntDAO.getEmpruntsEnCours().size()));
    }
    
    private void demarrerCarrousel() {
        carouselTimeline = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> changerImage())
        );
        carouselTimeline.setCycleCount(Timeline.INDEFINITE);
        carouselTimeline.play();
        
        // Appliquer la première image
        changerImage();
    }
    
    private void changerImage() {
        String imageUrl = carouselImages.get(currentImageIndex);
        carouselPane.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover; -fx-background-position: center;");
        
        // Animation de fondu
        FadeTransition fade = new FadeTransition(Duration.seconds(1), carouselPane);
        fade.setFromValue(0.8);
        fade.setToValue(1);
        fade.play();
        
        currentImageIndex = (currentImageIndex + 1) % carouselImages.size();
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
    private void goToEmprunt() throws IOException {
        SceneManager.showEmprunt();
    }
    
    @FXML
    private void goToAdmin() throws IOException {
        // Pour la gestion admin (CRUD complet)
        SceneManager.showBibliotheque();
    }
    
    @FXML
    private void toggleTheme() {
        if (themeToggle.getText().equals("🌙")) {
            appliquerThemeSombre();
            themeToggle.setText("☀️");
        } else {
            appliquerThemeClair();
            themeToggle.setText("🌙");
        }
    }
    
    private void appliquerThemeClair() {
        rootPane.setStyle("-fx-background-color: #f0f4f8;");
    }
    
    private void appliquerThemeSombre() {
        rootPane.setStyle("-fx-background-color: #1a2632;");
    }
}