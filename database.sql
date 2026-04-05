-- Table etudiant
CREATE TABLE IF NOT EXISTS etudiant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Table livre
CREATE TABLE IF NOT EXISTS livre (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    categorie VARCHAR(100),
    disponible BOOLEAN DEFAULT TRUE
);

-- Table emprunt
CREATE TABLE IF NOT EXISTS emprunt (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_etudiant INT NOT NULL,
    id_livre INT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour DATE NULL,
    FOREIGN KEY (id_etudiant) REFERENCES etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE
);

-- Table admin
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_login VARCHAR(50)
);

-- Insertion des données de test
INSERT INTO etudiant (nom, prenom, email) VALUES
('Dupont', 'Jean', 'jean.dupont@email.com'),
('Martin', 'Sophie', 'sophie.martin@email.com'),
('Bernard', 'Lucas', 'lucas.bernard@email.com'),
('Emprunteur', 'Anonyme', 'emprunt@bibliotech.com');

INSERT INTO livre (titre, auteur, categorie, disponible) VALUES
('Java pour les nuls', 'John Doe', 'Informatique', TRUE),
('Spring Boot Masterclass', 'Jane Smith', 'Informatique', TRUE),
('Les Misérables', 'Victor Hugo', 'Littérature', TRUE),
('Clean Code', 'Robert Martin', 'Informatique', TRUE),
('Le Petit Prince', 'Antoine de Saint-Exupéry', 'Littérature', TRUE),
('Histoire de la France', 'Jules Michelet', 'Histoire', TRUE),
('Mathématiques L3', 'Jean-Pierre Demailly', 'Mathématiques', TRUE),
('Physique Quantique', 'Albert Einstein', 'Physique', TRUE),
('1984', 'George Orwell', 'Littérature', TRUE),
('Le Guide du Java', 'Joshua Bloch', 'Informatique', TRUE),
('La Peste', 'Albert Camus', 'Littérature', TRUE),
('Algèbre Linéaire', 'Serge Lang', 'Mathématiques', TRUE);

INSERT INTO admin (username, password) VALUES
('admin', 'admin123'),
('bibliothecaire', 'biblio2024');