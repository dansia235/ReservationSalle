/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.config;

 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 
 import javax.annotation.PostConstruct;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 
 @Configuration
 public class DatabaseInitializer {
 
     @Value("${spring.datasource.url}")
     private String databaseUrl;
 
     @Value("${spring.datasource.username}")
     private String dbUsername;
 
     @Value("${spring.datasource.password}")
     private String dbPassword;
 
     @Value("${app.db.user.username}")
     private String newDbUsername;
 
     @Value("${app.db.user.password}")
     private String newDbPassword;
 
     @PostConstruct
     public void initialize() {
         System.out.println("URL de la base de données : " + databaseUrl);
         try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
             if (connection != null) {
                 System.out.println("Connexion à la base de données réussie.");
             }
         } catch (SQLException e) {
             System.err.println("Échec de la connexion à la base de données : " + e.getMessage());
         }
     }
 
     @Bean
     public void createDatabaseUser() {
         String checkUserSQL = "SELECT 1 FROM pg_roles WHERE rolname='" + newDbUsername + "';";
         String createUserSQL = "CREATE USER " + newDbUsername + " WITH PASSWORD '" + newDbPassword + "';";
         String grantPrivilegesSQL = "GRANT ALL PRIVILEGES ON DATABASE reservation_db TO " + newDbUsername + ";";
 
         try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
              Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery(checkUserSQL)) {
             if (rs.next()) {
                 System.out.println("L'utilisateur '" + newDbUsername + "' existe déjà.");
             } else {
                 stmt.execute(createUserSQL);
                 stmt.execute(grantPrivilegesSQL);
                 System.out.println("Utilisateur créé avec succès et privilèges accordés.");
             }
         } catch (SQLException e) {
             System.err.println("Échec de la création de l'utilisateur ou de l'attribution des privilèges : " + e.getMessage());
         }
     }
 }
 