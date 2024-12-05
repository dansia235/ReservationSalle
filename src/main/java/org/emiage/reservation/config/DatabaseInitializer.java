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
         System.out.println("Database URL: " + databaseUrl);
         try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
             if (connection != null) {
                 System.out.println("Connected to the database successfully.");
                 createDatabaseUser(connection, newDbUsername, newDbPassword);
             }
         } catch (SQLException e) {
             System.err.println("Failed to connect to the database: " + e.getMessage());
         }
     }
 
     private void createDatabaseUser(Connection connection, String username, String password) {
         String checkUserSQL = "SELECT 1 FROM pg_roles WHERE rolname='" + username + "';";
         String createUserSQL = "CREATE USER " + username + " WITH PASSWORD '" + password + "';";
         String grantPrivilegesSQL = "GRANT ALL PRIVILEGES ON DATABASE reservation_db TO " + username + ";";
 
         try (Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery(checkUserSQL)) {
             if (rs.next()) {
                 System.out.println("User '" + username + "' already exists.");
             } else {
                 stmt.execute(createUserSQL);
                 stmt.execute(grantPrivilegesSQL);
                 System.out.println("User created successfully with all privileges.");
             }
         } catch (SQLException e) {
             System.err.println("Error creating user or assigning privileges: " + e.getMessage());
         }
     }
 
     // New public method to be called from UserController
     public void createDatabaseUser(String username, String password) {
         try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
             if (connection != null) {
                 createDatabaseUser(connection, username, password);
             }
         } catch (SQLException e) {
             System.err.println("Failed to connect to the database: " + e.getMessage());
         }
     }
 }
 