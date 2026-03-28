package controller;

import dao.EmpruntDAO;
import dao.EtudiantDAO;
import dao.LivreDAO;
import model.Emprunt;
import model.Etudiant;
import model.Livre;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import utils.SceneManager;
import exceptions.DateEmpruntInvalideException;
import exceptions.LivreIndisponibleException;
import exceptions.UtilisateurNonTrouveException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class EmpruntController {
    
    @FXML
    private VBox rootPane;
    
    @FXML
    private ComboBox<Etudiant> comboEtudiant;
    
    @FXML
    private ComboBox<Livre> comboLivre;
    
    @FXML
    private DatePicker dateRetour;
    
    @FXML
    private TableView<Emprunt> tableEmprunts;
    
    @FXML
    private TableColumn<Emprunt, Integer> colId;
    
    @FXML
    private TableColumn<Emprunt, String> colEtudiant;
    
    @FXML
    private TableColumn<Emprunt, String> colLivre;
    
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateEmprunt;
    
    @FXML
    private TableColumn<Emprunt, LocalDate> colDateRetour;
    
    @FXML
    private TableColumn<Emprunt, Void> colAction;
    
    @FXML
    private ToggleButton themeToggle;
    
    private EtudiantDAO etudiantDAO;
    private LivreDAO livreDAO;
    private EmpruntDAO empruntDAO;
    
    private static Livre livreSelectionne;
    
    public static void setLivreSelectionne(Livre livre) {
        livreSelectionne = livre;
    }
    
    @FXML
    public void initialize() {
        etudiantDAO = new EtudiantDAO();
        livreDAO = new LivreDAO();
        empruntDAO = new EmpruntDAO();
        
        // Configurer les colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        colLivre.setCellValueFactory(new PropertyValueFactory<>("titreLivre"));
        colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        colDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        
        // Ajouter le bouton retourner
        ajouterBoutonRetour();
        
        // Charger les données
        chargerEtudiants();
        chargerLivres();
        chargerEmprunts();
        
        // Pré-sélectionner le livre si vient du catalogue
        if (livreSelectionne != null && livreSelectionne.isDisponible()) {
            comboLivre.getSelectionModel().select(livreSelectionne);
        }
        
        // Configurer la date de retour par défaut (7 jours max)
        dateRetour.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate maxDate = today.plusDays(7);
                setDisable(date.isBefore(today) || date.isAfter(maxDate));
            }
        });
        dateRetour.setValue(LocalDate.now().plusDays(3));
        
        appliquerThemeClair();
        themeToggle.setText("🌙");
        themeToggle.setOnAction(e -> toggleTheme());
    }
    
    private void ajouterBoutonRetour() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button retourBtn = new Button("↩️ Retourner");
            
            {
                retourBtn.getStyleClass().add("btn-return");
                retourBtn.setOnAction(event -> {
                    Emprunt emprunt = getTableView().getItems().get(getIndex());
                    try {
                        retournerLivre(emprunt);
                    } catch (LivreIndisponibleException e) {
                        showAlert("Erreur", e.getMessage(), Alert.AlertType.ERROR);
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Emprunt emprunt = getTableView().getItems().get(getIndex());
                    retourBtn.setDisable(emprunt.getDateRetour() != null);
                    setGraphic(retourBtn);
                }
            }
        });
    }
    
    private void chargerEtudiants() {
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        comboEtudiant.setItems(FXCollections.observableArrayList(etudiants));
    }
    
    private void chargerLivres() {
        List<Livre> livres = livreDAO.getLivresDisponibles();
        comboLivre.setItems(FXCollections.observableArrayList(livres));
    }
    
    private void chargerEmprunts() {
        List<Emprunt> emprunts = empruntDAO.getEmpruntsEnCours();
        tableEmprunts.setItems(FXCollections.observableArrayList(emprunts));
    }
    
    @FXML
    private void emprunterLivre() {
        try {
            Etudiant etudiant = comboEtudiant.getSelectionModel().getSelectedItem();
            Livre livre = comboLivre.getSelectionModel().getSelectedItem();
            LocalDate dateRetourValue = dateRetour.getValue();
            
            // Validation avec exceptions personnalisées
            if (etudiant == null) {
                throw new UtilisateurNonTrouveException("Aucun étudiant sélectionné");
            }
            
            if (livre == null) {
                throw new LivreIndisponibleException("Aucun livre sélectionné");
            }
            
            if (!livre.isDisponible()) {
                throw new LivreIndisponibleException(livre.getTitre(), livre.getId());
            }
            
            if (dateRetourValue == null) {
                throw new DateEmpruntInvalideException("Veuillez sélectionner une date de retour");
            }
            
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(7);
            
            if (dateRetourValue.isBefore(today) || dateRetourValue.isAfter(maxDate)) {
                throw new DateEmpruntInvalideException(dateRetourValue);
            }
            
            // Effectuer l'emprunt
            boolean succes = empruntDAO.emprunterLivre(etudiant.getId(), livre.getId(), today);
            
            if (!succes) {
                throw new LivreIndisponibleException(livre.getTitre(), livre.getId());
            }
            
            // Rafraîchir les données
            chargerLivres();
            chargerEmprunts();
            comboLivre.getSelectionModel().clearSelection();
            
            showAlert("Succès", "Emprunt enregistré avec succès !\n" +
                    "Livre: " + livre.getTitre() + "\n" +
                    "Étudiant: " + etudiant.getPrenom() + " " + etudiant.getNom() + "\n" +
                    "Date de retour: " + dateRetourValue, Alert.AlertType.INFORMATION);
                    
        } catch (UtilisateurNonTrouveException | LivreIndisponibleException | DateEmpruntInvalideException e) {
            showAlert("Erreur", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void retournerLivre(Emprunt emprunt) throws LivreIndisponibleException {
        if (emprunt.getDateRetour() != null) {
            throw new LivreIndisponibleException("Ce livre a déjà été retourné");
        }
        
        boolean succes = empruntDAO.retournerLivre(emprunt.getId());
        
        if (succes) {
            chargerLivres();
            chargerEmprunts();
            showAlert("Succès", "Retour enregistré avec succès !\n" +
                    "Livre: " + emprunt.getTitreLivre() + "\n" +
                    "Rendu par: " + emprunt.getNomEtudiant(), Alert.AlertType.INFORMATION);
        } else {
            throw new LivreIndisponibleException("Erreur lors du retour du livre");
        }
    }
    
    @FXML
    private void actualiserEmprunts() {
        chargerEmprunts();
        showAlert("Info", "Liste des emprunts actualisée", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void goToAccueil() throws IOException {
        SceneManager.showAccueil();
    }
    
    @FXML
    private void goToCatalogue() throws IOException {
        livreSelectionne = null;
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