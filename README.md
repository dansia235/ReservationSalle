# Application de Réservation de Salle de Réunion

## Description du Projet

Ce projet a pour objectif de développer une API RESTful permettant la gestion des réservations de salles de réunion en milieu professionnel. Les utilisateurs peuvent consulter la disponibilité des salles, réserver des créneaux horaires et gérer leurs réservations.

## Outils Utilisés

- **Environnement de Développement** : NetBeans 23
- **Java Development Kit (JDK)** : Version 17
- **Serveur d'Applications** : Apache Tomcat 11
- **Base de Données** : PostgreSQL 17

## Déploiement de l'Application

1. **Installation des Prérequis** :
   - Téléchargez et installez [NetBeans 23](https://netbeans.apache.org/download/index.html).
   - Installez le [JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
   - Téléchargez et configurez [Apache Tomcat 11](https://tomcat.apache.org/download-11.cgi).
   - Installez [PostgreSQL 17](https://www.postgresql.org/download/).

2. **Configuration de la Base de Données** :
   - Créez une nouvelle base de données PostgreSQL nommée `reservation_salle`.
   - Les tables de la base de données seront générées automatiquement lors du déploiement de l'application grâce à Spring Data JPA. Assurez-vous que les propriétés JPA sont correctement configurées pour permettre cette génération automatique. :contentReference[oaicite:0]{index=0}

3. **Configuration de l'Application** :
   - Clonez le dépôt du projet depuis GitHub.
   - Ouvrez le projet dans NetBeans.
   - Modifiez le fichier `application.properties` pour y renseigner les informations de connexion à la base de données, par exemple :

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/reservation_salle
     spring.datasource.username=postgres
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```

4. **Déploiement sur Tomcat** :
   - Générez le fichier WAR de l'application via NetBeans.
   - Déployez le fichier WAR sur le serveur Tomcat en le plaçant dans le répertoire `webapps`.

5. **Lancement de l'Application** :
   - Démarrez le serveur Tomcat.
   - Accédez à l'application via `http://localhost:8080/nom_de_votre_application`.

## Routes de l'API

### Utilisateurs (`User`)

- **Endpoints** :
  - `POST /api/users/register` : Créer un nouvel utilisateur
  - `GET /api/users` : Récupérer tous les utilisateurs
  - `GET /api/users/{username}` : Récupérer un utilisateur par nom d'utilisateur
  - `PUT /api/users/{id}` : Mettre à jour un utilisateur
  - `DELETE /api/users/{id}` : Supprimer un utilisateur

### Salles (`Room`)

- **Endpoints** :
  - `POST /api/rooms` : Créer une nouvelle salle
  - `GET /api/rooms` : Récupérer toutes les salles
  - `GET /api/rooms/{id}` : Récupérer une salle par ID
  - `PUT /api/rooms/{id}` : Mettre à jour une salle
  - `DELETE /api/rooms/{id}` : Supprimer une salle
  - `GET /api/rooms/{id}/reservations` : Récupérer les réservations d'une salle par ID

### Réservations (`Reservation`)

- **Endpoints** :
  - `POST /api/reservations` : Créer une nouvelle réservation
  - `GET /api/reservations` : Récupérer toutes les réservations
  - `GET /api/reservations/{id}` : Récupérer une réservation par ID
  - `PUT /api/reservations/{id}` : Mettre à jour une réservation
  - `DELETE /api/reservations/{id}` : Supprimer une réservation

### Disponibilité (`Availability`)

- **Endpoints** :
  - `GET /api/availability` : Récupérer les IDs des salles disponibles pour une plage de dates donnée
    - **Paramètres** :
      - `startDate` : Date de début.
      - `endDate` : Date de fin.

## Authentification

Toutes les requêtes aux endpoints de l'application nécessitent l'utilisation d'une clé API.

- **Header HTTP** : `x-api-key`
- **Valeur de la clé API** : `lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A=`