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
 import org.emiage.reservation.config.DatabaseInitializer;
 import org.emiage.reservation.exceptions.UserNotFoundException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 
 import java.util.List;
 
 @RestController
 @RequestMapping("/api/users")
 public class UserController {
 
     private final UserService userService;
     private final DatabaseInitializer databaseInitializer;
 
     @Autowired
     public UserController(UserService userService, DatabaseInitializer databaseInitializer) {
         this.userService = userService;
         this.databaseInitializer = databaseInitializer;
     }
 
     @PostMapping("/register")
     public ResponseEntity<User> registerUser(@RequestBody User user) {
         User savedUser = userService.save(user);
         databaseInitializer.createDatabaseUser(user.getUsername(), user.getPassword());
         return ResponseEntity.ok(savedUser);
     }
 
     @GetMapping("/{username}")
     public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
         User user = userService.findByUsername(username);
         if (user == null) {
             throw new UserNotFoundException("User not found with username: " + username);
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
 