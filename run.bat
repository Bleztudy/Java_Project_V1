@echo off
echo Lancement de BiblioTech...

set JAVAFX_PATH=C:\Users\saado\.m2\repository\org\openjfx

java --module-path "%JAVAFX_PATH%\javafx-base\21\javafx-base-21-win.jar;%JAVAFX_PATH%\javafx-graphics\21\javafx-graphics-21-win.jar;%JAVAFX_PATH%\javafx-controls\21\javafx-controls-21-win.jar;%JAVAFX_PATH%\javafx-fxml\21\javafx-fxml-21-win.jar" --add-modules javafx.base,javafx.graphics,javafx.controls,javafx.fxml -cp target\classes main.Main

pause