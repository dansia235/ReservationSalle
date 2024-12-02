# Application de Réservation de Salle de Réunion

## Phase d’Analyse

### Ressources du Web-Service

#### Utilisateurs (`User`)
- **Propriétés** :
  - `id` : Identifiant unique de l'utilisateur (Long)
  - `username` : Nom d'utilisateur (String)
  - `email` : Adresse email (String)
  - `password` : Mot de passe (String)

- **Routes (URI) et Méthodes d’Accès** :
  - `POST /api/users/register` : Créer un nouvel utilisateur
  - `GET /api/users` : Récupérer tous les utilisateurs
  - `GET /api/users/{username}` : Récupérer un utilisateur par nom d'utilisateur
  - `PUT /api/users/{id}` : Mettre à jour un utilisateur
  - `DELETE /api/users/{id}` : Supprimer un utilisateur

- **Authentification** :
  - Aucune authentification particulière requise pour ces méthodes.

#### Salles (`Room`)
- **Propriétés** :
  - `id` : Identifiant unique de la salle (Long)
  - `name` : Nom de la salle (String)
  - `capacity` : Capacité de la salle (int)
  - `location` : Emplacement de la salle (String)

- **Routes (URI) et Méthodes d’Accès** :
  - `POST /api/rooms` : Créer une nouvelle salle
  - `GET /api/rooms` : Récupérer toutes les salles
  - `GET /api/rooms/{id}` : Récupérer une salle par ID
  - `PUT /api/rooms/{id}` : Mettre à jour une salle
  - `DELETE /api/rooms/{id}` : Supprimer une salle

- **Authentification** :
  - Aucune authentification particulière requise pour ces méthodes.

#### Réservations (`Reservation`)
- **Propriétés** :
  - `id` : Identifiant unique de la réservation (Long)
  - `user` : Utilisateur ayant fait la réservation (User)
  - `room` : Salle réservée (Room)
  - `startDate` : Date de début de la réservation (Date)
  - `endDate` : Date de fin de la réservation (Date)

- **Routes (URI) et Méthodes d’Accès** :
  - `POST /api/reservations` : Créer une nouvelle réservation
  - `GET /api/reservations` : Récupérer toutes les réservations
  - `GET /api/reservations/{id}` : Récupérer une réservation par ID
  - `PUT /api/reservations/{id}` : Mettre à jour une réservation
  - `DELETE /api/reservations/{id}` : Supprimer une réservation

- **Authentification** :
  - Aucune authentification particulière requise pour ces méthodes.

### Tables Utilisées pour le Projet

- **Utilisateurs (`users`)**
  - `id` INTEGER PRIMARY KEY AUTOINCREMENT
  - `username` TEXT NOT NULL UNIQUE
  - `email` TEXT NOT NULL UNIQUE
  - `password` TEXT NOT NULL

- **Salles (`rooms`)**
  - `id` INTEGER PRIMARY KEY AUTOINCREMENT
  - `name` TEXT NOT NULL
  - `capacity` INTEGER NOT NULL
  - `location` TEXT NOT NULL

- **Réservations (`reservations`)**
  - `id` INTEGER PRIMARY KEY AUTOINCREMENT
  - `user_id` INTEGER NOT NULL
  - `room_id` INTEGER NOT NULL
  - `start_date` TEXT NOT NULL
  - `end_date` TEXT NOT NULL
  - FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
  - FOREIGN KEY (`room_id`) REFERENCES `rooms`(`id`)

## Conception de la Base et des Objets

### Création du Service

1. **Créer une Base de Données PostgreSQL 17**
   - Créez une base de données PostgreSQL 17 avec les tables décrites ci-dessus.
   - Injectez quelques données de test pour les utilisateurs, les salles et les réservations.

2. **Créer les Classes d’Entités et les Classes d’Accès à ces Entités**
   - Créez des classes d'entités pour `User`, `Room`, et `Reservation`.
   - Créez des classes de service pour accéder et manipuler ces entités.

3. **Concevoir les Ressources avec leurs Opérations**
   - Implémentez les contrôleurs pour gérer les opérations CRUD pour chaque ressource.
   - Utilisez les routes et les méthodes d’accès décrites dans la phase d’analyse.

### Identifiants de Connexion à la Base de Données

- **Identifiants par Défaut** :
  - **Nom d'utilisateur** : `postgres`
  - **Mot de passe** : `dansia`

- **Identifiants Générés** :
  - Un compte avec les identifiants `db_user` (nom d'utilisateur) et `dbpassword` (mot de passe) sera généré pour permettre aux utilisateurs de se connecter à la base de données et effectuer les opérations CRUD.

- **Privilèges des Utilisateurs** :
  - Les identifiants créés par les utilisateurs auront tous les privilèges nécessaires pour accéder à la base de données et effectuer les opérations CRUD.

## Exemple de Données de Test

### Utilisateurs
```sql
INSERT INTO users (username, email, password) VALUES ('johndoe', 'johndoe@example.com', 'password123');
INSERT INTO users (username, email, password) VALUES ('janedoe', 'janedoe@example.com', 'password456');