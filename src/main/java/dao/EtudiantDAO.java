package dao;

import model.Etudiant;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    
    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant ORDER BY id";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                etudiants.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }
    
    public void ajouterEtudiant(Etudiant e) {
        String sql = "INSERT INTO etudiant (nom, prenom, email) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, e.getNom());
            pstmt.setString(2, e.getPrenom());
            pstmt.setString(3, e.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void modifierEtudiant(Etudiant e) {
        String sql = "UPDATE etudiant SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, e.getNom());
            pstmt.setString(2, e.getPrenom());
            pstmt.setString(3, e.getEmail());
            pstmt.setInt(4, e.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void supprimerEtudiant(int id) {
        String sql = "DELETE FROM etudiant WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Etudiant> rechercherEtudiants(String motCle) {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            String recherche = "%" + motCle + "%";
            pstmt.setString(1, recherche);
            pstmt.setString(2, recherche);
            pstmt.setString(3, recherche);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                etudiants.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return etudiants;
    }
}