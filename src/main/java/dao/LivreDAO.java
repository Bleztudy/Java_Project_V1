package dao;

import model.Livre;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    
    public List<Livre> getAllLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre ORDER BY id";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setCategorie(rs.getString("categorie"));
                l.setDisponible(rs.getBoolean("disponible"));
                livres.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
    
    public List<Livre> getLivresDisponibles() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre WHERE disponible = TRUE ORDER BY titre";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setCategorie(rs.getString("categorie"));
                l.setDisponible(true);
                livres.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
    
    public void ajouterLivre(Livre l) {
        String sql = "INSERT INTO livre (titre, auteur, categorie, disponible) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, l.getTitre());
            pstmt.setString(2, l.getAuteur());
            pstmt.setString(3, l.getCategorie());
            pstmt.setBoolean(4, l.isDisponible());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                l.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void modifierLivre(Livre l) {
        String sql = "UPDATE livre SET titre = ?, auteur = ?, categorie = ?, disponible = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, l.getTitre());
            pstmt.setString(2, l.getAuteur());
            pstmt.setString(3, l.getCategorie());
            pstmt.setBoolean(4, l.isDisponible());
            pstmt.setInt(5, l.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void supprimerLivre(int id) {
        String sql = "DELETE FROM livre WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Livre> rechercherLivres(String motCle) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre WHERE titre LIKE ? OR auteur LIKE ? OR categorie LIKE ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            String recherche = "%" + motCle + "%";
            pstmt.setString(1, recherche);
            pstmt.setString(2, recherche);
            pstmt.setString(3, recherche);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setCategorie(rs.getString("categorie"));
                l.setDisponible(rs.getBoolean("disponible"));
                livres.add(l);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return livres;
    }
    
    public void updateDisponibilite(int idLivre, boolean disponible) {
        String sql = "UPDATE livre SET disponible = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setBoolean(1, disponible);
            pstmt.setInt(2, idLivre);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}