package controller;

import dao.EmpruntDAO;
import dao.LivreDAO;
import model.Etudiant;
import model.Livre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.SceneManager;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogueController {
    
    @FXML
    private VBox rootPane;
    
    @FXML
    private FlowPane livresGrid;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private ComboBox<String> filterCombo;
    
    private LivreDAO livreDAO;
    private List<Livre> tousLesLivres;
    
    @FXML
    public void initialize() {
        livreDAO = new LivreDAO();
        tousLesLivres = livreDAO.getAllLivres();
        
        // Configurer le filtre par catégorie
        filterCombo.getItems().addAll("Toutes", "Informatique", "Littérature", "Mathématiques", "Physique", "Histoire");
        filterCombo.setValue("Toutes");
        filterCombo.setOnAction(e -> filtrerLivres());
        
        afficherLivres(tousLesLivres);
    }
    
    private void filtrerLivres() {
        String categorie = filterCombo.getValue();
        if (categorie == null || categorie.equals("Toutes")) {
            afficherLivres(tousLesLivres);
        } else {
            List<Livre> filtres = tousLesLivres.stream()
                .filter(l -> categorie.equals(l.getCategorie()))
                .collect(Collectors.toList());
            afficherLivres(filtres);
        }
    }
    
    private void afficherLivres(List<Livre> livres) {
        livresGrid.getChildren().clear();
        
        for (Livre livre : livres) {
            VBox card = creerCarteLivre(livre);
            livresGrid.getChildren().add(card);
        }
    }
    
    private void lireLivre(Livre livre) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/lecteur.fxml"));
        Parent root = loader.load();
        
        LecteurController controller = loader.getController();
        
        // Contenu simulé du livre (tu peux remplacer par de vrais textes)
        String contenu = getContenuLivre(livre.getId());
        controller.setLivre(livre.getTitre(), contenu);
        
        Stage stage = new Stage();
        stage.setTitle(livre.getTitre());
        stage.setScene(new Scene(root, 800, 600));
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
        
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Erreur", "Impossible d'ouvrir le livre", Alert.AlertType.ERROR);
    }
}

    private String getContenuLivre(int id) {
        // Simuler des contenus de livres
        switch (id) {
            case 1:
                return "Java pour les nuls - Chapitre 1: Introduction à Java\n\n" +
                    "Java est un langage de programmation orienté objet créé par James Gosling chez Sun Microsystems en 1995. " +
                    "Il est conçu pour être portable, ce qui signifie que les programmes écrits en Java peuvent s'exécuter sur n'importe quelle plateforme " +
                    "disposant d'une machine virtuelle Java (JVM).\n\n" +
                    "Chapitre 2: Les bases de Java\n\n" +
                    "Les variables, les types de données, les opérateurs, les structures de contrôle... Java utilise une syntaxe similaire au C++.\n\n" +
                    "Chapitre 3: La programmation orientée objet\n\n" +
                    "Les classes, les objets, l'héritage, le polymorphisme, l'encapsulation sont des concepts fondamentaux en Java...";
            
            case 2:
                return "Spring Boot Masterclass\n\n" +
                    "Spring Boot est un framework Java qui facilite le développement d'applications d'entreprise. " +
                    "Il fournit une configuration automatique et des starters pour les dépendances courantes.\n\n" +
                    "Chapitre 1: Configuration initiale\n\n" +
                    "Spring Boot utilise une approche 'opinionated' pour réduire le code boilerplate...";
            
            case 3:
                return "Les Misérables - Victor Hugo\n\n" +
                    "PREMIÈRE PARTIE - FANTINE\n\n" +
                    "Livre premier - Un juste\n\n" +
                    "En 1815, M. Charles-François-Bienvenu Myriel était évêque de Digne. C'était un vieillard d'environ soixante-quinze ans; " +
                    "il occupait le siège de Digne depuis 1806.\n\n" +
                    "Chapitre II - Monsieur Myriel devient monseigneur Bienvenu\n\n" +
                    "Le palais épiscopal de Digne était attenant à l'hôpital. C'était un vaste et bel hôtel...";
            
            default:
                return "Contenu du livre: " + (id == 4 ? "Clean Code" : "Livre") + 
                    "\n\nCeci est un exemple de contenu. Dans une version complète, chaque livre aurait son propre contenu texte.\n\n" +
                    "Vous pouvez ajouter des fichiers texte pour chaque livre dans le dossier resources/books/";
        }
    }

    private void emprunterLivre(Livre livre) {
        // Créer un étudiant fictif automatiquement
        Etudiant etudiantFictif = new Etudiant(999, "Emprunteur", "Anonyme", "emprunt@bibliotech.com");
        
        // Enregistrer l'emprunt directement
        EmpruntDAO empruntDAO = new EmpruntDAO();
        boolean succes = empruntDAO.emprunterLivre(etudiantFictif.getId(), livre.getId(), java.time.LocalDate.now());
        
        if (succes) {
            showAlert("Succès", "Livre emprunté avec succès !\nÉtudiant: Emprunteur Anonyme\nLivre: " + livre.getTitre(), Alert.AlertType.INFORMATION);
            // Rafraîchir l'affichage du catalogue pour mettre à jour la disponibilité
            tousLesLivres = livreDAO.getAllLivres();
            filtrerLivres();
        } else {
            showAlert("Erreur", "Ce livre n'est plus disponible.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private VBox creerCarteLivre(Livre livre) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5); -fx-pref-width: 220; -fx-alignment: center;");
        
        // Image du livre
        ImageView bookImage = new ImageView();
        try {
            String imagePath = getClass().getResource("/images/books/" + livre.getId() + ".jpg").toExternalForm();
            Image image = new Image(imagePath, 120, 150, true, true);
            bookImage.setImage(image);
        } catch (Exception e) {
            // Image par défaut si non trouvée
            try {
                Image defaultImage = new Image(getClass().getResourceAsStream("/images/books/default.jpg"), 120, 150, true, true);
                bookImage.setImage(defaultImage);
            } catch (Exception ex) {
                Text fallback = new Text("📖");
                fallback.setStyle("-fx-font-size: 80px;");
                card.getChildren().add(fallback);
            }
        }
        bookImage.setFitWidth(120);
        bookImage.setFitHeight(150);
        bookImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        
        Text titre = new Text(livre.getTitre());
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #2c3e50; -fx-wrap-text: true;");
        
        Text auteur = new Text("Par " + livre.getAuteur());
        auteur.setStyle("-fx-font-size: 12px; -fx-fill: #7f8c8d;");
        
        Text statut = new Text(livre.isDisponible() ? "Disponible" : "Emprunté");
        statut.setStyle("-fx-background-color: " + (livre.isDisponible() ? "#27ae60" : "#e74c3c") + "; -fx-text-fill: white; -fx-padding: 4 12; -fx-background-radius: 20; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        // Boutons
        HBox buttons = new HBox(8);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);
        
        Button lireBtn = new Button("📖 Lire");
        lireBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 20; -fx-cursor: hand;");
        lireBtn.setOnAction(e -> lireLivre(livre));
        
        Button emprunterBtn = new Button("Emprunter");
        emprunterBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 20; -fx-cursor: hand;");
        emprunterBtn.setDisable(!livre.isDisponible());
        emprunterBtn.setOnAction(e -> emprunterLivre(livre));  
        
        buttons.getChildren().addAll(lireBtn, emprunterBtn);
        
        card.getChildren().addAll(bookImage, titre, auteur, statut, buttons);
        
        // Hover effect
        card.setOnMouseEntered(event -> {
            card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 20, 0, 0, 10); -fx-pref-width: 220; -fx-alignment: center;");
            card.setScaleX(1.02);
            card.setScaleY(1.02);
        });
        card.setOnMouseExited(event -> {
            card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5); -fx-pref-width: 220; -fx-alignment: center;");
            card.setScaleX(1);
            card.setScaleY(1);
        });
        
        return card;
    }
    
    
    @FXML
    private void rechercherLivres() {
        String recherche = searchField.getText().toLowerCase();
        if (recherche.isEmpty()) {
            filtrerLivres();
        } else {
            List<Livre> resultats = tousLesLivres.stream()
                .filter(l -> l.getTitre().toLowerCase().contains(recherche) ||
                             l.getAuteur().toLowerCase().contains(recherche) ||
                             (l.getCategorie() != null && l.getCategorie().toLowerCase().contains(recherche)))
                .collect(Collectors.toList());
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