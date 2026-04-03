package dao;

import model.Emprunt;
import model.Livre;
// import model.Etudiant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpruntDAO {
    
    private static List<Emprunt> emprunts = new ArrayList<>();
    private static int nextId = 2;
    private LivreDAO livreDAO = new LivreDAO();
    private EtudiantDAO etudiantDAO = new EtudiantDAO();
    
    static {
        Emprunt emp = new Emprunt();
        emp.setId(1);
        emp.setIdEtudiant(1);
        emp.setIdLivre(1);
        emp.setNomEtudiant("Jean Dupont");
        emp.setTitreLivre("Java pour les nuls");
        emp.setDateEmprunt(LocalDate.now().minusDays(5));
        emprunts.add(emp);
        
        // Marquer le livre comme emprunté
        new LivreDAO().updateDisponibilite(1, false);
    }
    
    public List<Emprunt> getAllEmprunts() {
        return new ArrayList<>(emprunts);
    }
    
    public List<Emprunt> getEmpruntsEnCours() {
        return emprunts.stream()
            .filter(e -> e.getDateRetour() == null)
            .collect(Collectors.toList());
    }
    
    public boolean emprunterLivre(int idEtudiant, int idLivre, LocalDate dateEmprunt) {
        List<Livre> livres = livreDAO.getAllLivres();
        
        boolean livreDisponible = livres.stream()
            .filter(l -> l.getId() == idLivre)
            .findFirst()
            .map(Livre::isDisponible)
            .orElse(false);
        
        if (!livreDisponible) {
            return false;
        }
        
        // Récupérer les infos de l'étudiant et du livre
        String nomEtudiant = etudiantDAO.getAllEtudiants().stream()
            .filter(e -> e.getId() == idEtudiant)
            .findFirst()
            .map(e -> e.getPrenom() + " " + e.getNom())
            .orElse("Inconnu");
        
        String titreLivre = livres.stream()
            .filter(l -> l.getId() == idLivre)
            .findFirst()
            .map(Livre::getTitre)
            .orElse("Inconnu");
        
        Emprunt emp = new Emprunt();
        emp.setId(nextId++);
        emp.setIdEtudiant(idEtudiant);
        emp.setIdLivre(idLivre);
        emp.setNomEtudiant(nomEtudiant);
        emp.setTitreLivre(titreLivre);
        emp.setDateEmprunt(dateEmprunt);
        emprunts.add(emp);
        
        livreDAO.updateDisponibilite(idLivre, false);
        return true;
    }
    
    public boolean retournerLivre(int idEmprunt) {
        for (Emprunt emp : emprunts) {
            if (emp.getId() == idEmprunt) {
                emp.setDateRetour(LocalDate.now());
                livreDAO.updateDisponibilite(emp.getIdLivre(), true);
                return true;
            }
        }
        return false;
    }
    
    public boolean isLivreEmprunte(int idLivre) {
        return emprunts.stream()
            .anyMatch(e -> e.getIdLivre() == idLivre && e.getDateRetour() == null);
    }
}