package controller;

import dao.*;
import model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import utils.ThemeManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminDashboardController {
    
    @FXML
    private HBox rootPane;
    
    @FXML
    private Label adminName;
    
    @FXML
    private Label currentLogin;
    
    @FXML
    private Label lastLogin;
    
    @FXML
    private ComboBox<String> themeCombo;
    
    // Composants étudiants
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
    
    // Composants livres
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
    
    private EtudiantDAO etudiantDAO;
    private LivreDAO livreDAO;
    
    private static String currentAdmin;
    private static String lastLoginDate;
    
    public static void setCurrentAdmin(String admin) {
        currentAdmin = admin;
    }
    
    public static void setLastLogin(String last) {
        lastLoginDate = last;
    }
    
    @FXML
    public void initialize() {
        etudiantDAO = new EtudiantDAO();
        livreDAO = new LivreDAO();
        
        // Configurer les colonnes étudiants
        colIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Configurer les colonnes livres
        colIdLivre.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        
        // Charger les données
        chargerEtudiants();
        chargerLivres();
        
        // Configurer les sélections
        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> selectionnerEtudiant(newVal));
        tableLivres.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> selectionnerLivre(newVal));
        
        // Informations admin
        adminName.setText(currentAdmin);
        currentLogin.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lastLogin.setText(lastLoginDate != null ? lastLoginDate : "-");
        
        // Thèmes
        themeCombo.getItems().addAll(ThemeManager.getThemes().keySet());
        themeCombo.setValue(ThemeManager.getCurrentTheme());
    }
    
    @FXML
    private void changeTheme() {
        String theme = themeCombo.getValue();
        ThemeManager.applyTheme((javafx.scene.layout.VBox) rootPane.getChildren().get(1), theme);
    }
    
    @FXML
    private void handleLogout() throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Déconnexion");
        confirm.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");
        confirm.setContentText("L'application va se fermer.");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    
    // ==================== GESTION ÉTUDIANTS ====================
    
    private void chargerEtudiants() {
        tableEtudiants.setItems(FXCollections.observableArrayList(etudiantDAO.getAllEtudiants()));
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
        if (!txtNom.getText().isEmpty() && !txtPrenom.getText().isEmpty() && !txtEmail.getText().isEmpty()) {
            Etudiant e = new Etudiant(txtNom.getText(), txtPrenom.getText(), txtEmail.getText());
            etudiantDAO.ajouterEtudiant(e);
            chargerEtudiants();
            viderChampsEtudiant();
            showAlert("Succès", "Étudiant ajouté", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void modifierEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null && !txtNom.getText().isEmpty()) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            etudiantDAO.modifierEtudiant(selected);
            chargerEtudiants();
            viderChampsEtudiant();
            showAlert("Succès", "Étudiant modifié", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void supprimerEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            etudiantDAO.supprimerEtudiant(selected.getId());
            chargerEtudiants();
            viderChampsEtudiant();
            showAlert("Succès", "Étudiant supprimé", Alert.AlertType.INFORMATION);
        }
    }
    
    private void viderChampsEtudiant() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
    }
    
    // ==================== GESTION LIVRES ====================
    
    private void chargerLivres() {
        tableLivres.setItems(FXCollections.observableArrayList(livreDAO.getAllLivres()));
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
        if (!txtTitre.getText().isEmpty() && !txtAuteur.getText().isEmpty()) {
            Livre l = new Livre(txtTitre.getText(), txtAuteur.getText(), txtCategorie.getText(), chkDisponible.isSelected());
            livreDAO.ajouterLivre(l);
            chargerLivres();
            viderChampsLivre();
            showAlert("Succès", "Livre ajouté", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void modifierLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null && !txtTitre.getText().isEmpty()) {
            selected.setTitre(txtTitre.getText());
            selected.setAuteur(txtAuteur.getText());
            selected.setCategorie(txtCategorie.getText());
            selected.setDisponible(chkDisponible.isSelected());
            livreDAO.modifierLivre(selected);
            chargerLivres();
            viderChampsLivre();
            showAlert("Succès", "Livre modifié", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void supprimerLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            livreDAO.supprimerLivre(selected.getId());
            chargerLivres();
            viderChampsLivre();
            showAlert("Succès", "Livre supprimé", Alert.AlertType.INFORMATION);
        }
    }
    
    private void viderChampsLivre() {
        txtTitre.clear();
        txtAuteur.clear();
        txtCategorie.clear();
        chkDisponible.setSelected(true);
    }
    
    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}