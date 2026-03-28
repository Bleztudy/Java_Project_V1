package controller;

import dao.LivreDAO;
import model.Livre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import utils.SceneManager;


import java.io.IOException;
import java.util.List;

public class CatalogueController {
    
    @FXML
    private VBox rootPane;
    
    @FXML
    private FlowPane livresGrid;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private ToggleButton themeToggle;
    
    private LivreDAO livreDAO;
    private List<Livre> tousLesLivres;
    
    @FXML
    public void initialize() {
        livreDAO = new LivreDAO();
        tousLesLivres = livreDAO.getAllLivres();
        afficherLivres(tousLesLivres);
        appliquerThemeClair();
        
        themeToggle.setText("🌙");
        themeToggle.setOnAction(e -> toggleTheme());
    }
    
    private void afficherLivres(List<Livre> livres) {
        livresGrid.getChildren().clear();
        
        for (Livre livre : livres) {
            VBox card = creerCarteLivre(livre);
            livresGrid.getChildren().add(card);
        }
    }
    
    private VBox creerCarteLivre(Livre livre) {
        VBox card = new VBox(10);
        card.getStyleClass().add("book-card");
        
        // Emoji représentant le livre
        Text emoji = new Text(livre.isDisponible() ? "📖" : "🔒");
        emoji.setStyle("-fx-font-size: 48px;");
        
        Text titre = new Text(livre.getTitre());
        titre.getStyleClass().add("book-title");
        
        Text auteur = new Text("Par " + livre.getAuteur());
        auteur.getStyleClass().add("book-author");
        
        Text categorie = new Text(livre.getCategorie() != null ? livre.getCategorie() : "Général");
        categorie.setStyle("-fx-font-size: 10px; -fx-fill: #7f8c8d;");
        
        HBox actions = new HBox(5);
        actions.getStyleClass().add("book-actions");
        
        Button wishlistBtn = new Button("❤️");
        wishlistBtn.getStyleClass().add("btn-wishlist");
        wishlistBtn.setOnAction(e -> ajouterWishlist(livre));
        
        Button readBtn = new Button("📖 Lire");
        readBtn.getStyleClass().add("btn-read");
        readBtn.setOnAction(e -> lireLivre(livre));
        
        Button borrowBtn = new Button("📚 Emprunter");
        borrowBtn.getStyleClass().add("btn-borrow");
        borrowBtn.setDisable(!livre.isDisponible());
        borrowBtn.setOnAction(e -> {
            try {
                emprunterLivre(livre);
            } catch (IOException ex) {
                showAlert("Erreur", "Impossible d'accéder à la page d'emprunt", Alert.AlertType.ERROR);
            }
        });
        
        actions.getChildren().addAll(wishlistBtn, readBtn, borrowBtn);
        
        card.getChildren().addAll(emoji, titre, auteur, categorie, actions);
        return card;
    }
    
    private void ajouterWishlist(Livre livre) {
        showAlert("Wishlist", livre.getTitre() + " a été ajouté à votre wishlist !", Alert.AlertType.INFORMATION);
    }
    
    private void lireLivre(Livre livre) {
        showAlert("Lecture", "Ouverture de \"" + livre.getTitre() + "\"...", Alert.AlertType.INFORMATION);
        // Ici tu pourrais ouvrir un lecteur PDF ou une nouvelle fenêtre
    }
    
    private void emprunterLivre(Livre livre) throws IOException {
        if (!livre.isDisponible()) {
            showAlert("Erreur", "Ce livre n'est pas disponible", Alert.AlertType.ERROR);
            return;
        }
        // Stocker le livre sélectionné pour le formulaire d'emprunt
        EmpruntController.setLivreSelectionne(livre);
        SceneManager.showEmprunt();
    }
    
    @FXML
    private void rechercherLivres() {
        String recherche = searchField.getText().toLowerCase();
        if (recherche.isEmpty()) {
            afficherLivres(tousLesLivres);
        } else {
            List<Livre> resultats = livreDAO.rechercherLivres(recherche);
            afficherLivres(resultats);
            if (resultats.isEmpty()) {
                showAlert("Info", "Aucun livre trouvé", Alert.AlertType.INFORMATION);
            }
        }
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
    
    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}