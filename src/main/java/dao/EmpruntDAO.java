package dao;

import model.Emprunt;
import model.Livre;
import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    
    public List<Emprunt> getAllEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, et.nom as nom_etudiant, et.prenom, l.titre as titre_livre " +
                     "FROM emprunt e " +
                     "JOIN etudiant et ON e.id_etudiant = et.id " +
                     "JOIN livre l ON e.id_livre = l.id " +
                     "ORDER BY e.date_emprunt DESC";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Emprunt emp = new Emprunt();
                emp.setId(rs.getInt("id"));
                emp.setIdEtudiant(rs.getInt("id_etudiant"));
                emp.setIdLivre(rs.getInt("id_livre"));
                emp.setNomEtudiant(rs.getString("prenom") + " " + rs.getString("nom_etudiant"));
                emp.setTitreLivre(rs.getString("titre_livre"));
                emp.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                Date dateRetour = rs.getDate("date_retour");
                if (dateRetour != null) {
                    emp.setDateRetour(dateRetour.toLocalDate());
                }
                emprunts.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
    
    public List<Emprunt> getEmpruntsEnCours() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, et.nom as nom_etudiant, et.prenom, l.titre as titre_livre " +
                     "FROM emprunt e " +
                     "JOIN etudiant et ON e.id_etudiant = et.id " +
                     "JOIN livre l ON e.id_livre = l.id " +
                     "WHERE e.date_retour IS NULL " +
                     "ORDER BY e.date_emprunt DESC";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Emprunt emp = new Emprunt();
                emp.setId(rs.getInt("id"));
                emp.setIdEtudiant(rs.getInt("id_etudiant"));
                emp.setIdLivre(rs.getInt("id_livre"));
                emp.setNomEtudiant(rs.getString("prenom") + " " + rs.getString("nom_etudiant"));
                emp.setTitreLivre(rs.getString("titre_livre"));
                emp.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                emprunts.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
    
    public boolean emprunterLivre(int idEtudiant, int idLivre, LocalDate dateEmprunt) {
        LivreDAO livreDAO = new LivreDAO();
        
        // Vérifier si le livre est disponible
        List<Livre> livres = livreDAO.getAllLivres();
        boolean livreDisponible = livres.stream()
            .filter(l -> l.getId() == idLivre)
            .findFirst()
            .map(Livre::isDisponible)
            .orElse(false);
        
        if (!livreDisponible) {
            return false;
        }
        
        // Créer l'emprunt
        String sql = "INSERT INTO emprunt (id_etudiant, id_livre, date_emprunt) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idEtudiant);
            pstmt.setInt(2, idLivre);
            pstmt.setDate(3, Date.valueOf(dateEmprunt));
            pstmt.executeUpdate();
            
            // Marquer le livre comme indisponible
            livreDAO.updateDisponibilite(idLivre, false);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean retournerLivre(int idEmprunt) {
        // Récupérer l'ID du livre associé à l'emprunt
        String selectSql = "SELECT id_livre FROM emprunt WHERE id = ?";
        int idLivre = -1;
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(selectSql)) {
            pstmt.setInt(1, idEmprunt);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idLivre = rs.getInt("id_livre");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
        if (idLivre == -1) {
            return false;
        }
        
        // Mettre à jour la date de retour
        String updateSql = "UPDATE emprunt SET date_retour = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(updateSql)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.setInt(2, idEmprunt);
            pstmt.executeUpdate();
            
            // Marquer le livre comme disponible
            LivreDAO livreDAO = new LivreDAO();
            livreDAO.updateDisponibilite(idLivre, true);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean isLivreEmprunte(int idLivre) {
        String sql = "SELECT COUNT(*) FROM emprunt WHERE id_livre = ? AND date_retour IS NULL";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idLivre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}