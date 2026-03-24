@echo off
set PATH_TO_FX=target\classes
mvn clean compile
java --module-path "C:\Users\saado\.m2\repository\org\openjfx\javafx-controls\21\javafx-controls-21-win.jar;C:\Users\saado\.m2\repository\org\openjfx\javafx-fxml\21\javafx-fxml-21-win.jar" --add-modules javafx.controls,javafx.fxml -cp target\classes main.Main