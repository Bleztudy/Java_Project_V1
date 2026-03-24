package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Etudiant;

public class BibliothequeController {

    @FXML
    private TableView<Etudiant> tableEtudiants;
    
    @FXML
    private TableColumn<Etudiant, String> colNom;
    
    @FXML
    private TableColumn<Etudiant, String> colPrenom;
    
    @FXML
    private TableColumn<Etudiant, String> colEmail;
    
    @FXML
    private TableColumn<Etudiant, String> colFiliere;
    
    @FXML
    private TableColumn<Etudiant, String> colNiveau;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFiliere;

    @FXML
    private TextField txtNiveau;
    
    private ObservableList<Etudiant> etudiantsList;
    
    // Initialisation du controller
    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de l'objet Etudiant
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colFiliere.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        
        // Initialiser la liste
        etudiantsList = FXCollections.observableArrayList();

        // Ajouter des données fictives
        chargerDonneesFictives();
        
        // Lier la liste à la table
        tableEtudiants.setItems(etudiantsList);
    }
    
    private void chargerDonneesFictives() {
        etudiantsList.addAll(
            new Etudiant("Dupont", "Jean", "jean.dupont@email.com", "Informatique", "L3"),
            new Etudiant("Martin", "Sophie", "sophie.martin@email.com", "Mathématiques", "M1"),
            new Etudiant("Bernard", "Lucas", "lucas.bernard@email.com", "Physique", "L2"),
            new Etudiant("Petit", "Emma", "emma.petit@email.com", "Informatique", "L3"),
            new Etudiant("Robert", "Thomas", "thomas.robert@email.com", "Mathématiques", "M2")
        );
    }
    
    @FXML
    private void ajouterEtudiant() {
        if (verifierChamps()) {
            Etudiant nouvelEtudiant = new Etudiant(
                txtNom.getText(),
                txtPrenom.getText(),
                txtEmail.getText(),
                txtFiliere.getText(),
                txtNiveau.getText()
            );
            etudiantsList.add(nouvelEtudiant);
            viderChamps();
        }
    }
    
    @FXML
    private void modifierEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null && verifierChamps()) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            selected.setFiliere(txtFiliere.getText());
            selected.setNiveau(txtNiveau.getText());
            tableEtudiants.refresh();
            viderChamps();
        }
    }
    
    @FXML
    private void supprimerEtudiant() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            etudiantsList.remove(selected);
            viderChamps();
        }
    }
    
    @FXML
    private void rechercherEtudiant() {
        String recherche = txtNom.getText().toLowerCase();
        if (recherche.isEmpty()) {
            tableEtudiants.setItems(etudiantsList);
        } else {
            ObservableList<Etudiant> resultats = FXCollections.observableArrayList();
            for (Etudiant e : etudiantsList) {
                if (e.getNom().toLowerCase().contains(recherche) || 
                    e.getPrenom().toLowerCase().contains(recherche)) {
                    resultats.add(e);
                }
            }
            tableEtudiants.setItems(resultats);
        }
    }
    
    @FXML
    private void actualiser() {
        tableEtudiants.setItems(etudiantsList);
        viderChamps();
    }
    
    @FXML
    private void chargerChampsSelection() {
        Etudiant selected = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtNom.setText(selected.getNom());
            txtPrenom.setText(selected.getPrenom());
            txtEmail.setText(selected.getEmail());
            txtFiliere.setText(selected.getFiliere());
            txtNiveau.setText(selected.getNiveau());
        }
    }
    
    private void viderChamps() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtFiliere.clear();
        txtNiveau.clear();
    }
    
    private boolean verifierChamps() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() ||
            txtEmail.getText().isEmpty() || txtFiliere.getText().isEmpty() ||
            txtNiveau.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return false;
        }
        return true;
    }
}