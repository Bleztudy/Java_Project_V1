package controller;

import dao.AdminDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.SceneManager;
import java.io.IOException;

public class AdminLoginController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorLabel;
    
    private AdminDAO adminDAO;
    
    @FXML
    public void initialize() {
        adminDAO = new AdminDAO();
    }
    
    @FXML
    private void goBack() throws IOException {
        SceneManager.showAccueil();
    }
    
    @FXML
    private void goToAdmin() throws IOException {
        SceneManager.showAdminLogin();
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (adminDAO.authenticate(username, password)) {
            adminDAO.updateLastLogin(username);
            AdminDashboardController.setCurrentAdmin(username);
            AdminDashboardController.setLastLogin(adminDAO.getLastLogin(username));
            SceneManager.showAdminDashboard();
        } else {
            errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect");
            passwordField.clear();
        }
    }
}