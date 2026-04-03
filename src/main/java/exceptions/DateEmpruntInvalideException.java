package exceptions;

import java.time.LocalDate;

public class DateEmpruntInvalideException extends Exception {
    public DateEmpruntInvalideException(String message) {
        super(message);
    }
    
    public DateEmpruntInvalideException(LocalDate dateRetour) {
        super("La date de retour (" + dateRetour + ") doit être comprise entre aujourd'hui et 7 jours maximum.");
    }
}