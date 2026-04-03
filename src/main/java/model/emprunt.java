package model;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int idEtudiant;
    private int idLivre;
    private String nomEtudiant;
    private String titreLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    
    public Emprunt() {}
    
    public Emprunt(int id, int idEtudiant, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.idEtudiant = idEtudiant;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdEtudiant() { return idEtudiant; }
    public void setIdEtudiant(int idEtudiant) { this.idEtudiant = idEtudiant; }
    
    public int getIdLivre() { return idLivre; }
    public void setIdLivre(int idLivre) { this.idLivre = idLivre; }
    
    public String getNomEtudiant() { return nomEtudiant; }
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }
    
    public String getTitreLivre() { return titreLivre; }
    public void setTitreLivre(String titreLivre) { this.titreLivre = titreLivre; }
    
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    
    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
}