package controller;

import dao.*;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class BibliothequeController {

    @FXML
    private VBox rootPane;
    
    @FXML
    private ToggleButton themeToggle;
    
    // Composants pour les étudiants
    @FXML
    private TableView<Etudiant> tableEtudiants;
    @FXML
    private TableColumn<Etudiant, Integer> colIdEtudiant;
    @FXML
    private TableColumn<Etudiant, String> colNom;
    @FXML
    private TableColumn<Etudiant, String> colPrenom;
    @FXML
    private TableColumn<Etudiant, String> colEmail;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    
    // Composants pour les livres
    @FXML
    private TableView<Livre> tableLivres;
    @FXML
    private TableColumn<Livre, Integer> colIdLivre;
    @FXML
    private TableColumn<Livre, String> colTitre;
    @FXML
    private TableColumn<Livre, String> colAuteur;
    @FXML
    private TableColumn<Livre, String> colCategorie;
    @FXML
    private TableColumn<Livre, Boolean> colDisponible;
    @FXML
    private TextField txtTitre;
    @FXML
    private TextField txtAuteur;
    @FXML
    private TextField txtCategorie;
    @FXML
    private CheckBox chkDisponible;
    
    // Composants pour les emprunts
    @FXML
    private TableView<Emprunt> tableEmprunts;
    @FXML
    private TableColumn<Emprunt, Integer> colIdEmprunt;
    @FXML
    private TableColumn<Emprunt, String> colEtudiant;
    @FXML
    private TableColumn<Emprunt, String> colLivre;
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateEmprunt;
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateRetour;
    @FXML
    private ComboBox<Etudiant> comboEtudiant;
    @FXML
    private ComboBox<Livre> comboLivre;
    @FXML
    private DatePicker dateEmprunt;
    
    private EtudiantDAO etudiantDAO;
    private LivreDAO livreDAO;
    private EmpruntDAO empruntDAO;
    
    private ObservableList<Etudiant> etudiantsList;
    private ObservableList<Livre> livresList;
    private ObservableList<Emprunt> empruntsList;
    
    private boolean darkMode = false;
    
    @FXML
    public void initialize() {
        // Initialiser les DAO
        etudiantDAO = new EtudiantDAO();
        livreDAO = new LivreDAO();
        empruntDAO = new EmpruntDAO();
        
        // Configurer les colonnes des étudiants
        colIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Configurer les colonnes des livres
        colIdLivre.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        
        // Configurer les colonnes des emprunts
        colIdEmprunt.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        colLivre.setCellValueFactory(new PropertyValueFactory<>("titreLivre"));
        colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        colDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        
        // Initialiser les listes observables
        etudiantsList = FXCollections.observableArrayList();
        livresList = FXCollections.observableArrayList();
        empruntsList = FXCollections.observableArrayList();
        
        // Charger les données
        chargerEtudiants();
        chargerLivres();
        chargerEmprunts();
        
        // Configurer les ComboBox
        comboEtudiant.setItems(etudiantsList);
        comboLivre.setItems(livresList);
        
        // Configurer la date par défaut
        dateEmprunt.setValue(LocalDate.now());
        
        // Ajouter les listeners pour la sélection
        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> selectionnerEtudiant(newVal));
        
        tableLivres.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> selectionnerLivre(newVal));
        
        themeToggle.setText("🌞 Mode clair");
    }
    
    // ==================== GESTION DES ÉTUDIANTS ====================
    
    private void chargerEtudiants() {
        etudiantsList.clear();
        etudiantsList.addAll(etudiantDAO.getAllEtudiants());
        tableEtudiants.setItems(etudiantsList);
    }
    
    private void selectionnerEtudiant(Etudiant e) {
        if (e != null) {
            txtNom.setText(e.getNom());
            txtPrenom.setText(e.getPrenom());
            txtEmail.setText(e.getEmail());
        }
    }
    
    @FXML
    private void ajouterEtudiant() {
        if (verifierChampsEtudiant()) {
            Etudiant e = new Etudiant(txtNom.getText(), txtPrenom.getText(), txtEmail.getText());
            etudiantDAO.ajouterEtudiant(e);
            chargerEtudiants();
            viderChampsEtudiant();
            showAlert("Succès", "Étudiant ajouté avec succès !", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void modifierEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null && verifierChampsEtudiant()) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            etudiantDAO.modifierEtudiant(selected);
            chargerEtudiants();
            viderChampsEtudiant();
            showAlert("Succès", "Étudiant modifié avec succès !", Alert.AlertType.INFORMATION);
        } else if (selected == null) {
            showAlert("Erreur", "Veuillez sélectionner un étudiant.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void supprimerEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Supprimer l'étudiant");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer " + selected.getPrenom() + " " + selected.getNom() + " ?");
            
            if (confirmation.showAndWait().get() == ButtonType.OK) {
                etudiantDAO.supprimerEtudiant(selected.getId());
                chargerEtudiants();
                viderChampsEtudiant();
                showAlert("Succès", "Étudiant supprimé avec succès !", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un étudiant.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void rechercherEtudiant() {
        String recherche = txtNom.getText();
        if (recherche.isEmpty()) {
            chargerEtudiants();
        } else {
            List<Etudiant> resultats = etudiantDAO.rechercherEtudiants(recherche);
            etudiantsList.clear();
            etudiantsList.addAll(resultats);
            if (resultats.isEmpty()) {
                showAlert("Info", "Aucun étudiant trouvé.", Alert.AlertType.INFORMATION);
            }
        }
    }
    
    private void viderChampsEtudiant() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
    }
    
    private boolean verifierChampsEtudiant() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs !", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    // ==================== GESTION DES LIVRES ====================
    
    private void chargerLivres() {
        livresList.clear();
        livresList.addAll(livreDAO.getAllLivres());
        tableLivres.setItems(livresList);
        // Mettre à jour la ComboBox des livres (seulement disponibles)
        comboLivre.setItems(FXCollections.observableArrayList(livreDAO.getLivresDisponibles()));
    }
    
    private void selectionnerLivre(Livre l) {
        if (l != null) {
            txtTitre.setText(l.getTitre());
            txtAuteur.setText(l.getAuteur());
            txtCategorie.setText(l.getCategorie());
            chkDisponible.setSelected(l.isDisponible());
        }
    }
    
    @FXML
    private void ajouterLivre() {
        if (verifierChampsLivre()) {
            Livre l = new Livre(txtTitre.getText(), txtAuteur.getText(), txtCategorie.getText(), chkDisponible.isSelected());
            livreDAO.ajouterLivre(l);
            chargerLivres();
            viderChampsLivre();
            showAlert("Succès", "Livre ajouté avec succès !", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void modifierLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null && verifierChampsLivre()) {
            selected.setTitre(txtTitre.getText());
            selected.setAuteur(txtAuteur.getText());
            selected.setCategorie(txtCategorie.getText());
            selected.setDisponible(chkDisponible.isSelected());
            livreDAO.modifierLivre(selected);
            chargerLivres();
            viderChampsLivre();
            showAlert("Succès", "Livre modifié avec succès !", Alert.AlertType.INFORMATION);
        } else if (selected == null) {
            showAlert("Erreur", "Veuillez sélectionner un livre.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void supprimerLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (empruntDAO.isLivreEmprunte(selected.getId())) {
                showAlert("Erreur", "Ce livre est actuellement emprunté et ne peut pas être supprimé.", Alert.AlertType.ERROR);
                return;
            }
            
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Supprimer le livre");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer " + selected.getTitre() + " ?");
            
            if (confirmation.showAndWait().get() == ButtonType.OK) {
                livreDAO.supprimerLivre(selected.getId());
                chargerLivres();
                viderChampsLivre();
                showAlert("Succès", "Livre supprimé avec succès !", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un livre.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void rechercherLivre() {
        String recherche = txtTitre.getText();
        if (recherche.isEmpty()) {
            chargerLivres();
        } else {
            List<Livre> resultats = livreDAO.rechercherLivres(recherche);
            livresList.clear();
            livresList.addAll(resultats);
            if (resultats.isEmpty()) {
                showAlert("Info", "Aucun livre trouvé.", Alert.AlertType.INFORMATION);
            }
        }
    }
    
    private void viderChampsLivre() {
        txtTitre.clear();
        txtAuteur.clear();
        txtCategorie.clear();
        chkDisponible.setSelected(true);
    }
    
    private boolean verifierChampsLivre() {
        if (txtTitre.getText().isEmpty() || txtAuteur.getText().isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir le titre et l'auteur !", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    // ==================== GESTION DES EMPRUNTS ====================
    
    private void chargerEmprunts() {
        empruntsList.clear();
        empruntsList.addAll(empruntDAO.getEmpruntsEnCours());
        tableEmprunts.setItems(empruntsList);
    }
    
    @FXML
    private void emprunterLivre() {
        Etudiant etudiant = comboEtudiant.getSelectionModel().getSelectedItem();
        Livre livre = comboLivre.getSelectionModel().getSelectedItem();
        LocalDate date = dateEmprunt.getValue();
        
        if (etudiant == null || livre == null || date == null) {
            showAlert("Erreur", "Veuillez sélectionner un étudiant, un livre et une date.", Alert.AlertType.WARNING);
            return;
        }
        
        if (!livre.isDisponible()) {
            showAlert("Erreur", "Ce livre n'est pas disponible.", Alert.AlertType.ERROR);
            return;
        }
        
        boolean succes = empruntDAO.emprunterLivre(etudiant.getId(), livre.getId(), date);
        
        if (succes) {
            chargerLivres(); // Recharger pour mettre à jour la disponibilité
            chargerEmprunts(); // Recharger la liste des emprunts
            showAlert("Succès", "Emprunt enregistré avec succès !", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "L'emprunt a échoué.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void retournerLivre() {
        Emprunt emprunt = tableEmprunts.getSelectionModel().getSelectedItem();
        
        if (emprunt == null) {
            showAlert("Erreur", "Veuillez sélectionner un emprunt dans la liste.", Alert.AlertType.WARNING);
            return;
        }
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Retour du livre");
        confirmation.setContentText("Confirmez-vous le retour de \"" + emprunt.getTitreLivre() + "\" par " + emprunt.getNomEtudiant() + " ?");
        
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            boolean succes = empruntDAO.retournerLivre(emprunt.getId());
            
            if (succes) {
                chargerLivres(); // Recharger pour mettre à jour la disponibilité
                chargerEmprunts(); // Recharger la liste des emprunts
                showAlert("Succès", "Retour enregistré avec succès !", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Le retour a échoué.", Alert.AlertType.ERROR);
            }
        }
    }
    
    @FXML
    private void actualiserEmprunts() {
        chargerEmprunts();
        showAlert("Info", "Liste des emprunts actualisée.", Alert.AlertType.INFORMATION);
    }
    
    // ==================== MODE SOMBRE ====================
    
    @FXML
    public void toggleTheme() {
        if (darkMode) {
            rootPane.setStyle("-fx-background-color: #f0f4f8; -fx-padding: 20;");
            themeToggle.setText("🌞 Mode clair");
            darkMode = false;
        } else {
            rootPane.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");
            themeToggle.setText("🌙 Mode sombre");
            darkMode = true;
        }
    }
    
    // ==================== UTILITAIRES ====================
    
    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}