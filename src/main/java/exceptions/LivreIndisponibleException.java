package exceptions;

public class LivreIndisponibleException extends Exception {
    public LivreIndisponibleException(String message) {
        super(message);
    }
    
    public LivreIndisponibleException(String titre, int id) {
        super("Le livre '" + titre + "' (ID: " + id + ") n'est pas disponible pour l'emprunt.");
    }
}