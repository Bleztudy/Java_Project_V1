package dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AdminDAO {
    private static Map<String, String> admins = new HashMap<>();
    private static Map<String, String> lastLogins = new HashMap<>();
    
    static {
        admins.put("admin", "admin123");
        admins.put("bibliothecaire", "biblio2024");
    }
    
    public boolean authenticate(String username, String password) {
        return admins.containsKey(username) && admins.get(username).equals(password);
    }
    
    public void updateLastLogin(String username) {
        lastLogins.put(username, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
    
    public String getLastLogin(String username) {
        return lastLogins.getOrDefault(username, null);
    }
}