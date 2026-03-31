package utils;

import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private static String currentTheme = "light";
    private static final Map<String, String> themes = new HashMap<>();
    
    static {
        themes.put("light", "-fx-background-color: #f0f4f8;");
        themes.put("dark", "-fx-background-color: #1a2632;");
        themes.put("ocean", "-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);");
        themes.put("sunset", "-fx-background-color: linear-gradient(to bottom, #ff6a00, #ee0979);");
        themes.put("forest", "-fx-background-color: linear-gradient(to bottom, #134e5e, #71b280);");
        themes.put("midnight", "-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);");
    }
    
    public static void applyTheme(VBox rootPane, String theme) {
        currentTheme = theme;
        rootPane.setStyle(themes.getOrDefault(theme, themes.get("light")));
    }
    
    public static String getCurrentTheme() {
        return currentTheme;
    }
    
    public static Map<String, String> getThemes() {
        return themes;
    }
}