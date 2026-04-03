package exceptions;

public class UtilisateurNonTrouveException extends Exception {
    public UtilisateurNonTrouveException(String message) {
        super(message);
    }
    
    public UtilisateurNonTrouveException(int id) {
        super("Aucun étudiant trouvé avec l'ID: " + id);
    }
}