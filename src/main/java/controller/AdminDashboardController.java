package controller;

import dao.*;
import model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
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
    
    // Étudiants
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
    
    // Livres
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
    @FXML
    private Label imageStatus;
    @FXML
    private ImageView imagePreview;
    
    // Emprunts
    @FXML
    private ComboBox<Etudiant> comboEtudiant;
    @FXML
    private ComboBox<Livre> comboLivre;
    @FXML
    private DatePicker dateRetour;
    @FXML
    private TableView<Emprunt> tableEmprunts;
    @FXML
    private TableColumn<Emprunt, Integer> colIdEmprunt;
    @FXML
    private TableColumn<Emprunt, String> colNomEtudiant;
    @FXML
    private TableColumn<Emprunt, String> colTitreLivre;
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateEmprunt;
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateRetourEmprunt;
    @FXML
    private TableColumn<Emprunt, Void> colActionEmprunt;
    
    private EtudiantDAO etudiantDAO;
    private LivreDAO livreDAO;
    private EmpruntDAO empruntDAO;
    private String currentImagePath = null;
    
    private static String currentAdmin;
    private static String lastLoginDate;
    private static Livre livrePourEmprunt;
    
    public static void setCurrentAdmin(String admin) { currentAdmin = admin; }
    public static void setLastLogin(String last) { lastLoginDate = last; }
    public static void setLivrePourEmprunt(Livre livre) { livrePourEmprunt = livre; }
    
    @FXML
    public void initialize() {
        etudiantDAO = new EtudiantDAO();
        livreDAO = new LivreDAO();
        empruntDAO = new EmpruntDAO();
        
        // Configurer colonnes étudiants
        colIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Configurer colonnes livres
        colIdLivre.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        
        // Configurer colonnes emprunts
        colIdEmprunt.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        colTitreLivre.setCellValueFactory(new PropertyValueFactory<>("titreLivre"));
        colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        colDateRetourEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        
        ajouterBoutonRetourEmprunt();
        
        // Charger les données
        chargerEtudiants();
        chargerLivres();
        chargerEmprunts();
        chargerComboBox();
        
        if (livrePourEmprunt != null && livrePourEmprunt.isDisponible()) {
            comboLivre.getSelectionModel().select(livrePourEmprunt);
            livrePourEmprunt = null;
        }
        
        // Listeners
        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> selectionnerEtudiant(newVal));
        tableLivres.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> selectionnerLivre(newVal));
        
        // Infos admin
        adminName.setText(currentAdmin != null ? currentAdmin : "Admin");
        currentLogin.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lastLogin.setText(lastLoginDate != null ? lastLoginDate : "-");
        
        // Thèmes
        themeCombo.getItems().addAll("Clair", "Sombre", "Océan");
        themeCombo.setValue("Clair");
        
        // Date retour par défaut
        dateRetour.setValue(LocalDate.now().plusDays(3));
        dateRetour.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate maxDate = today.plusDays(7);
                setDisable(date.isBefore(today) || date.isAfter(maxDate));
            }
        });
    }
    
    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour le livre");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                String userDir = System.getProperty("user.dir");
                File destDir = new File(userDir + "/src/main/resources/images/books/");
                if (!destDir.exists()) destDir.mkdirs();
                
                Livre selected = tableLivres.getSelectionModel().getSelectedItem();
                String fileName = (selected != null) ? selected.getId() + ".jpg" : "temp_" + System.currentTimeMillis() + ".jpg";
                File destFile = new File(destDir, fileName);
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                currentImagePath = destFile.getPath();
                Image image = new Image(destFile.toURI().toString(), 100, 120, true, true);
                imagePreview.setImage(image);
                imageStatus.setText("Image: " + fileName);
                showAlert("Succès", "Image chargée !", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                showAlert("Erreur", "Erreur: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    
    private void ajouterBoutonRetourEmprunt() {
        colActionEmprunt.setCellFactory(param -> new TableCell<>() {
            private final Button retourBtn = new Button("Retourner");
            {
                retourBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 5 15; -fx-background-radius: 20; -fx-cursor: hand;");
                retourBtn.setOnAction(event -> {
                    Emprunt emprunt = getTableView().getItems().get(getIndex());
                    empruntDAO.retournerLivre(emprunt.getId());
                    chargerLivres();
                    chargerEmprunts();
                    chargerComboBox();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Emprunt emprunt = getTableView().getItems().get(getIndex());
                    retourBtn.setDisable(emprunt.getDateRetour() != null);
                    setGraphic(retourBtn);
                }
            }
        });
    }
    
    private void chargerComboBox() {
        comboEtudiant.setItems(FXCollections.observableArrayList(etudiantDAO.getAllEtudiants()));
        comboLivre.setItems(FXCollections.observableArrayList(livreDAO.getLivresDisponibles()));
    }
    
    private void chargerEmprunts() {
        tableEmprunts.setItems(FXCollections.observableArrayList(empruntDAO.getEmpruntsEnCours()));
    }
    
    @FXML
    private void emprunterLivre() {
        Etudiant etudiant = comboEtudiant.getSelectionModel().getSelectedItem();
        Livre livre = comboLivre.getSelectionModel().getSelectedItem();
        
        if (etudiant == null || livre == null) {
            showAlert("Erreur", "Sélectionnez un étudiant et un livre", Alert.AlertType.ERROR);
            return;
        }
        
        if (empruntDAO.emprunterLivre(etudiant.getId(), livre.getId(), LocalDate.now())) {
            chargerLivres();
            chargerEmprunts();
            chargerComboBox();
            showAlert("Succès", "Emprunt enregistré !", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Livre non disponible", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void actualiserEmprunts() {
        chargerEmprunts();
        chargerComboBox();
    }
    
    @FXML
    private void changeTheme() {
        String theme = themeCombo.getValue();
        if ("Sombre".equals(theme)) rootPane.setStyle("-fx-background-color: #1a2632; -fx-padding: 20; -fx-spacing: 20;");
        else if ("Océan".equals(theme)) rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43); -fx-padding: 20; -fx-spacing: 20;");
        else rootPane.setStyle("-fx-background-color: #f0f4f8; -fx-padding: 20; -fx-spacing: 20;");
    }
    
    @FXML
    private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Déconnexion");
        confirm.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");
        confirm.setContentText("L'application va se fermer.");
        if (confirm.showAndWait().get() == ButtonType.OK) System.exit(0);
    }
    
    // GESTION ÉTUDIANTS
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
            etudiantDAO.ajouterEtudiant(new Etudiant(txtNom.getText(), txtPrenom.getText(), txtEmail.getText()));
            chargerEtudiants();
            chargerComboBox();
            clearEtudiantFields();
        }
    }
    
    @FXML
    private void modifierEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            etudiantDAO.modifierEtudiant(selected);
            chargerEtudiants();
            chargerComboBox();
            clearEtudiantFields();
        }
    }
    
    @FXML
    private void supprimerEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            etudiantDAO.supprimerEtudiant(selected.getId());
            chargerEtudiants();
            chargerComboBox();
            clearEtudiantFields();
        }
    }
    
    private void clearEtudiantFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
    }
    
    // GESTION LIVRES
    private void chargerLivres() {
        tableLivres.setItems(FXCollections.observableArrayList(livreDAO.getAllLivres()));
    }
    
    private void selectionnerLivre(Livre l) {
        if (l != null) {
            txtTitre.setText(l.getTitre());
            txtAuteur.setText(l.getAuteur());
            txtCategorie.setText(l.getCategorie());
            chkDisponible.setSelected(l.isDisponible());
            
            // Charger l'image existante
            try {
                String imagePath = "/images/books/" + l.getId() + ".jpg";
                var is = getClass().getResourceAsStream(imagePath);
                if (is != null) {
                    imagePreview.setImage(new Image(is, 100, 120, true, true));
                    imageStatus.setText("Image existante");
                } else {
                    imagePreview.setImage(null);
                    imageStatus.setText("Aucune image");
                }
            } catch (Exception e) {
                imagePreview.setImage(null);
                imageStatus.setText("Aucune image");
            }
        }
    }
    
    @FXML
    private void ajouterLivre() {
        if (!txtTitre.getText().isEmpty() && !txtAuteur.getText().isEmpty()) {
            Livre l = new Livre(txtTitre.getText(), txtAuteur.getText(), txtCategorie.getText(), chkDisponible.isSelected());
            livreDAO.ajouterLivre(l);
            chargerLivres();
            chargerComboBox();
            clearLivreFields();
            showAlert("Succès", "Livre ajouté !", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void modifierLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setTitre(txtTitre.getText());
            selected.setAuteur(txtAuteur.getText());
            selected.setCategorie(txtCategorie.getText());
            selected.setDisponible(chkDisponible.isSelected());
            livreDAO.modifierLivre(selected);
            chargerLivres();
            chargerComboBox();
            clearLivreFields();
            showAlert("Succès", "Livre modifié !", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void supprimerLivre() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            livreDAO.supprimerLivre(selected.getId());
            chargerLivres();
            chargerComboBox();
            clearLivreFields();
            showAlert("Succès", "Livre supprimé !", Alert.AlertType.INFORMATION);
        }
    }
    
    private void clearLivreFields() {
        txtTitre.clear();
        txtAuteur.clear();
        txtCategorie.clear();
        chkDisponible.setSelected(true);
        imagePreview.setImage(null);
        imageStatus.setText("Aucune image");
        currentImagePath = null;
    }
    
    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}