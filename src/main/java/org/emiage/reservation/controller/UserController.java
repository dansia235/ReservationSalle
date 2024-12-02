/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */


 package org.emiage.reservation.controller;

 import org.emiage.reservation.model.User;
 import org.emiage.reservation.service.UserService;
 import org.emiage.reservation.exceptions.UserNotFoundException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.List;
 
 @RestController
 @RequestMapping("/api/users")
 public class UserController {
 
     private final UserService userService;
 
     @Autowired
     public UserController(UserService userService) {
         this.userService = userService;
     }
 
     @PostMapping("/register")
     public ResponseEntity<User> registerUser(@RequestBody User user) {
         User savedUser = userService.save(user);
         createDatabaseUser(user.getUsername(), user.getPassword());
         return ResponseEntity.ok(savedUser);
     }
 
     private void createDatabaseUser(String username, String password) {
         String databaseUrl = "jdbc:postgresql://localhost:5432/reservation_db";
         String dbUsername = "postgres";
         String dbPassword = "dansia";
 
         String checkUserSQL = "SELECT 1 FROM pg_roles WHERE rolname='" + username + "';";
         String createUserSQL = "CREATE USER " + username + " WITH PASSWORD '" + password + "';";
         String grantPrivilegesSQL = "GRANT ALL PRIVILEGES ON DATABASE reservation_db TO " + username + ";";
 
         try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
              Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery(checkUserSQL)) {
             if (rs.next()) {
                 System.out.println("L'utilisateur '" + username + "' existe déjà.");
             } else {
                 stmt.execute(createUserSQL);
                 stmt.execute(grantPrivilegesSQL);
                 System.out.println("Utilisateur créé avec succès et privilèges accordés.");
             }
         } catch (SQLException e) {
             System.err.println("Échec de la création de l'utilisateur ou de l'attribution des privilèges : " + e.getMessage());
         }
     }
 
     @GetMapping("/{username}")
     public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
         User user = userService.findByUsername(username);
         if (user == null) {
             throw new UserNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
         }
         return ResponseEntity.ok(user);
     }
 
     @GetMapping
     public ResponseEntity<List<User>> getAllUsers() {
         List<User> users = userService.findAll();
         return ResponseEntity.ok(users);
     }
 
     @PutMapping("/{id}")
     public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
         User updatedUser = userService.update(id, user);
         return ResponseEntity.ok(updatedUser);
     }
 
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
         userService.delete(id);
         return ResponseEntity.noContent().build();
     }
 }
 