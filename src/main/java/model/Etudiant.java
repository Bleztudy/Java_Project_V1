package model;

public class Etudiant {
    private String nom;
    private String prenom;
    private String email;
    private String filiere;
    private String niveau;
    
    public Etudiant() {}
    
    public Etudiant(String nom, String prenom, String email, String filiere, String niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.filiere = filiere;
        this.niveau = niveau;
    }
    
    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
}