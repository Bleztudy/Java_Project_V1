package dao;

import model.Livre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LivreDAO {
    
    private static List<Livre> livres = new ArrayList<>();
    private static int nextId = 5;
    
    static {
        livres.add(new Livre(1, "Java pour les nuls", "John Doe", "Informatique", true));
        livres.add(new Livre(2, "Spring Boot Masterclass", "Jane Smith", "Informatique", true));
        livres.add(new Livre(3, "Les Misérables", "Victor Hugo", "Littérature", true));
        livres.add(new Livre(4, "Clean Code", "Robert Martin", "Informatique", true));
        livres.add(new Livre(5, "Le Petit Prince", "Antoine de Saint-Exupéry", "Littérature", true));
        livres.add(new Livre(6, "Histoire de la France", "Jules Michelet", "Histoire", true));
        livres.add(new Livre(7, "Mathématiques L3", "Jean-Pierre Demailly", "Mathématiques", true));
        livres.add(new Livre(8, "Physique Quantique", "Albert Einstein", "Physique", true));
        livres.add(new Livre(9, "1984", "George Orwell", "Littérature", true));
        livres.add(new Livre(10, "Le Guide du Java", "Joshua Bloch", "Informatique", true));
        livres.add(new Livre(11, "La Peste", "Albert Camus", "Littérature", true));
        livres.add(new Livre(12, "Algèbre Linéaire", "Serge Lang", "Mathématiques", true));
    }
    
    public List<Livre> getAllLivres() {
        return new ArrayList<>(livres);
    }
    
    public List<Livre> getLivresDisponibles() {
        return livres.stream()
            .filter(Livre::isDisponible)
            .collect(Collectors.toList());
    }
    
    public void ajouterLivre(Livre l) {
        l.setId(nextId++);
        livres.add(l);
    }
    
    public void modifierLivre(Livre l) {
        for (int i = 0; i < livres.size(); i++) {
            if (livres.get(i).getId() == l.getId()) {
                livres.set(i, l);
                break;
            }
        }
    }
    
    public void supprimerLivre(int id) {
        livres.removeIf(l -> l.getId() == id);
    }
    
    public List<Livre> rechercherLivres(String motCle) {
        return livres.stream()
            .filter(l -> l.getTitre().toLowerCase().contains(motCle.toLowerCase()) ||
                         l.getAuteur().toLowerCase().contains(motCle.toLowerCase()) ||
                         (l.getCategorie() != null && l.getCategorie().toLowerCase().contains(motCle.toLowerCase())))
            .collect(Collectors.toList());
    }
    
    public void updateDisponibilite(int idLivre, boolean disponible) {
        for (Livre l : livres) {
            if (l.getId() == idLivre) {
                l.setDisponible(disponible);
                break;
            }
        }
    }
}