## Règles Git

- Toujours faire git pull avant de coder
- Ne pas modifier les fichiers des autres sans prévenir
- Faire des commits clairs


# 📚 Application JavaFX – Gestion de Bibliothèque

## 📌 Description du projet

Ce projet consiste à développer une application de gestion de bibliothèque universitaire.
L’objectif est de permettre la gestion des livres, des étudiants et des emprunts à travers une interface graphique développée avec JavaFX et connectée à une base de données MySQL via JDBC.

---

## 🎯 Objectifs pédagogiques

Ce projet permet de :

* Créer une interface graphique avec JavaFX
* Manipuler une base de données relationnelle avec MySQL
* Utiliser JDBC pour connecter Java à la base de données
* Implémenter des opérations CRUD (Create, Read, Update, Delete)
* Gérer des relations entre plusieurs tables

---

## 🛠️ Technologies utilisées

* Java 17+
* JavaFX
* MySQL
* JDBC
* Maven

---

## 🗄️ Structure de la base de données

### 📘 Table `livre`

* id : identifiant du livre
* titre : titre du livre
* auteur : auteur du livre
* categorie : catégorie
* disponible : disponibilité (true/false)

### 👨‍🎓 Table `etudiant`

* id : identifiant
* nom : nom
* prenom : prénom
* email : email

### 🔄 Table `emprunt`

* id : identifiant
* id_etudiant : référence à l’étudiant
* id_livre : référence au livre
* date_emprunt : date d’emprunt
* date_retour : date de retour

---

## 🖥️ Fonctionnalités principales

### 📚 Gestion des livres

* Ajouter un livre
* Modifier un livre
* Supprimer un livre
* Rechercher un livre
* Afficher la liste des livres

### 👨‍🎓 Gestion des étudiants

* Ajouter un étudiant
* Modifier un étudiant
* Supprimer un étudiant
* Afficher la liste des étudiants

### 🔄 Gestion des emprunts

* Emprunter un livre
* Retourner un livre
* Empêcher l’emprunt d’un livre indisponible
* Afficher les emprunts en cours

---

## 🧱 Structure du projet

```
java_project/
 ├── .mvn/
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   │   ├── model/
 │   │   │   │   ├── Livre.java
 │   │   │   │   ├── Etudiant.java
 │   │   │   │   └── Emprunt.java
 │   │   │   │
 │   │   │   ├── dao/
 │   │   │   │   ├── LivreDAO.java
 │   │   │   │   ├── EtudiantDAO.java
 │   │   │   │   └── EmpruntDAO.java
 │   │   │   │
 │   │   │   ├── controller/
 │   │   │   │   └── BibliothequeController.java
 │   │   │   │
 │   │   │   ├── database/
 │   │   │   │   └── DatabaseConnection.java
 │   │   │   │
 │   │   │   └── Main.java
 │   │   │
 │   │   └── resources/
 │   │       └── view/
 │   │           └── bibliotheque.fxml
 ├── target/
 ├── pom.xml
 ├── README.md
 └── database.sql

```

---

## 🚀 Installation et exécution

### 1. Cloner le projet

```
git clone https://github.com/Bleztudy/Java_Project.git
```

### 2. Configurer la base de données

* Lancer MySQL (via WampServer ou autre)
* Importer le fichier `database.sql`

### 3. Configurer la connexion

Modifier les informations dans :

```
database/DatabaseConnection.java
```

### 4. Lancer l’application

* Ouvrir le projet avec VS Code ou IntelliJ
* Exécuter la classe `Main.java`

---

## 👥 Organisation du travail

* Model : gestion des classes métiers (Livre, Etudiant, Emprunt)
* DAO : accès aux données (CRUD)
* Controller : logique de l’application
* View : interface graphique JavaFX

---

## ⚠️ Règles de collaboration (Git)

* Toujours faire `git pull` avant de commencer
* Faire des commits clairs
* Ne pas modifier le code des autres sans prévenir

---

## 📌 Auteur

Projet réalisé dans le cadre d’un travail académique en JavaFX et JDBC.
