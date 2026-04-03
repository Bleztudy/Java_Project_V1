package dao;

import model.Etudiant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EtudiantDAO {
    
    // Données fictives en attendant la base de données
    private static List<Etudiant> etudiants = new ArrayList<>();
    private static int nextId = 4;
    
    static {
        etudiants.add(new Etudiant(1, "Dupont", "Jean", "jean.dupont@email.com"));
        etudiants.add(new Etudiant(2, "Martin", "Sophie", "sophie.martin@email.com"));
        etudiants.add(new Etudiant(3, "Bernard", "Lucas", "lucas.bernard@email.com"));
        etudiants.add(new Etudiant(999, "Emprunteur", "Anonyme", "emprunt@bibliotech.com"));  // Étudiant fictif
        nextId = 1000;
    }
    
    public List<Etudiant> getAllEtudiants() {
        return new ArrayList<>(etudiants);
    }
    
    public void ajouterEtudiant(Etudiant e) {
        e.setId(nextId++);
        etudiants.add(e);
    }
    
    public void modifierEtudiant(Etudiant e) {
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getId() == e.getId()) {
                etudiants.set(i, e);
                break;
            }
        }
    }
    
    public void supprimerEtudiant(int id) {
        etudiants.removeIf(e -> e.getId() == id);
    }
    
    public List<Etudiant> rechercherEtudiants(String motCle) {
        return etudiants.stream()
            .filter(e -> e.getNom().toLowerCase().contains(motCle.toLowerCase()) ||
                         e.getPrenom().toLowerCase().contains(motCle.toLowerCase()) ||
                         e.getEmail().toLowerCase().contains(motCle.toLowerCase()))
            .collect(Collectors.toList());
    }
}