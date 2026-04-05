package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LecteurController {
    
    @FXML
    private Label titreLivre;
    
    @FXML
    private Text contenuLivre;
    
    @FXML
    private TextField pageField;
    
    @FXML
    private Label totalPages;
    @SuppressWarnings("unused")
    private String titre;
    private String[] pages;
    private int currentPage = 0;
    
    public void setLivre(String titre, String contenu) {
        this.titre = titre;
        this.pages = genererPages(contenu);
        this.totalPages.setText(String.valueOf(pages.length));
        this.titreLivre.setText(titre);
        afficherPage(0);
    }
    
    private String[] genererPages(String contenu) {
        int longueurPage = 500;
        int nombrePages = (int) Math.ceil((double) contenu.length() / longueurPage);
        String[] pages = new String[nombrePages];
        
        for (int i = 0; i < nombrePages; i++) {
            int debut = i * longueurPage;
            int fin = Math.min(debut + longueurPage, contenu.length());
            pages[i] = contenu.substring(debut, fin);
        }
        return pages;
    }
    
    private void afficherPage(int index) {
        if (index >= 0 && index < pages.length) {
            currentPage = index;
            contenuLivre.setText(pages[currentPage]);
            pageField.setText(String.valueOf(currentPage + 1));
        }
    }
    
    @FXML
    private void pagePrecedente() {
        if (currentPage > 0) {
            afficherPage(currentPage - 1);
        }
    }
    
    @FXML
    private void pageSuivante() {
        if (currentPage < pages.length - 1) {
            afficherPage(currentPage + 1);
        }
    }
    
    @FXML
    private void goToPage() {
        try {
            int page = Integer.parseInt(pageField.getText()) - 1;
            if (page >= 0 && page < pages.length) {
                afficherPage(page);
            }
        } catch (NumberFormatException e) {
            // Ignorer
        }
    }
    
    @FXML
    private void fermerLecteur() {
        Stage stage = (Stage) pageField.getScene().getWindow();
        stage.close();
    }
}