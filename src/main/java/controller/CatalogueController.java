package controller;

import dao.LivreDAO;
import model.Livre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
    
    private LivreDAO livreDAO;
    private List<Livre> tousLesLivres;
    
    @FXML
    public void initialize() {
        livreDAO = new LivreDAO();
        tousLesLivres = livreDAO.getAllLivres();
        afficherLivres(tousLesLivres);
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
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5); -fx-pref-width: 220; -fx-alignment: center;");
        
        Text emoji = new Text(livre.isDisponible() ? "📖" : "🔒");
        emoji.setStyle("-fx-font-size: 48px;");
        
        Text titre = new Text(livre.getTitre());
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #2c3e50; -fx-wrap-text: true;");
        
        Text auteur = new Text("Par " + livre.getAuteur());
        auteur.setStyle("-fx-font-size: 12px; -fx-fill: #7f8c8d;");
        
        Text statut = new Text(livre.isDisponible() ? "Disponible" : "Emprunté");
        statut.setStyle("-fx-background-color: " + (livre.isDisponible() ? "#27ae60" : "#e74c3c") + "; -fx-text-fill: white; -fx-padding: 4 12; -fx-background-radius: 20; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        // Note : On enlève le bouton Emprunter car maintenant c'est l'admin qui gère les emprunts
        // L'utilisateur normal ne peut pas emprunter directement
        
        card.getChildren().addAll(emoji, titre, auteur, statut);
        
        // Hover effect
        card.setOnMouseEntered(event -> card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 20, 0, 0, 10); -fx-pref-width: 220; -fx-alignment: center; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"));
        card.setOnMouseExited(event -> card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5); -fx-pref-width: 220; -fx-alignment: center;"));
        
        return card;
    }
    
    @FXML
    private void rechercherLivres() {
        String recherche = searchField.getText().toLowerCase();
        if (recherche.isEmpty()) {
            afficherLivres(tousLesLivres);
        } else {
            List<Livre> resultats = livreDAO.rechercherLivres(recherche);
            afficherLivres(resultats);
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
    private void goToAdmin() throws IOException {
        SceneManager.showAdminLogin();
    }
}