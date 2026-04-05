# 📚 BiblioTech - Application de Gestion de Bibliothèque

## Description

BiblioTech est une application de gestion de bibliothèque universitaire développée avec JavaFX et MySQL. Elle permet la gestion des étudiants, des livres et des emprunts via une interface graphique moderne et intuitive.

## Technologies utilisées

- **Java 17** - Langage de programmation
- **JavaFX 21** - Interface graphique
- **MySQL** - Base de données relationnelle
- **Maven** - Gestion des dépendances et build
- **CSS** - Styling de l'interface

## Fonctionnalités

### 👥 Gestion des étudiants
- Ajouter, modifier, supprimer des étudiants
- Recherche d'étudiants par nom, prénom ou email
- Affichage en tableau interactif

### 📖 Gestion des livres
- Ajouter, modifier, supprimer des livres
- Upload d'images pour chaque livre
- Recherche par titre, auteur ou catégorie
- Filtrage par catégorie
- Catalogue avec vignettes et images

### 🔄 Gestion des emprunts
- Emprunter un livre (création automatique d'un étudiant fictif)
- Retourner un livre
- Liste des emprunts en cours
- Validation des dates de retour (max 7 jours)
- Empêche l'emprunt d'un livre déjà pris

### 🔐 Administration
- Page de connexion sécurisée
- Dashboard admin avec 3 onglets (Étudiants, Livres, Emprunts)
- Thèmes personnalisables (Clair, Sombre, Océan)
- Affichage des dates de connexion

### 🏠 Page d'accueil
- Carrousel animé avec dégradés de couleurs
- Statistiques en temps réel (livres, étudiants, emprunts)
- Navigation simplifiée

### 📖 Lecteur de livres
- Fenêtre dédiée avec pagination
- Navigation page précédente/suivante
- Saut de page direct

## Structure du projet
src/main/java/
├── controller/ # Contrôleurs JavaFX
│ ├── AccueilController.java
│ ├── AdminDashboardController.java
│ ├── AdminLoginController.java
│ ├── CatalogueController.java
│ └── LecteurController.java
├── dao/ # Accès aux données
│ ├── AdminDAO.java
│ ├── EmpruntDAO.java
│ ├── EtudiantDAO.java
│ └── LivreDAO.java
├── database/ # Connexion MySQL
│ └── DatabaseConnection.java
├── model/ # Entités
│ ├── Emprunt.java
│ ├── Etudiant.java
│ └── Livre.java
├── utils/ # Utilitaires
│ ├── SceneManager.java
│ └── ThemeManager.java
├── exceptions/ # Exceptions personnalisées
└── main/ # Point d'entrée
└── Main.java

src/main/resources/
├── view/ # Fichiers FXML
│ ├── accueil.fxml
│ ├── admin_dashboard.fxml
│ ├── admin_login.fxml
│ ├── catalogue.fxml
│ └── lecteur.fxml
├── images/ # Images
│ ├── books/ # Images des livres
│ └── carousel/ # Images du carrousel
└── styles.css # Styles CSS

## Installation

### Prérequis

- Java 17 ou supérieur
- Maven
- MySQL (WampServer, XAMPP ou serveur distant)

### Étapes d'installation

1. **Cloner le dépôt**
```bash
git clone https://github.com/Bleztudy/Java_Project_V1.git
cd Java_Project_V1
Importer la base de données

Ouvrir phpMyAdmin

Créer une base de données bibliotech

Importer le fichier database.sql

Configurer la connexion MySQL

Modifier src/main/java/database/DatabaseConnection.java

Renseigner vos identifiants MySQL

Compiler et lancer l'application


mvn clean compile
mvn javafx:run
Identifiants de connexion
Rôle	Identifiant	Mot de passe
Administrateur	admin	admin123
Bibliothécaire	bibliothecaire	biblio2024
Base de données distante (optionnelle)
Une base de données distante est disponible pour les tests :

Host : sql5.freesqldatabase.com

Port : 3306

Database : sql5822300

Username : sql5822300

Password : (contacter l'administrateur)

Dépendances Maven
xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>

Version
1.0.0 - Avril 2026

Licence
Projet universitaire - Tous droits réservés