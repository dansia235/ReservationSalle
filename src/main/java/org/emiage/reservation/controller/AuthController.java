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
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.web.bind.annotation.*;
 
 @RestController
 @RequestMapping("/api")
 public class AuthController {
 
     private final UserService userService;
 
     @Autowired
     public AuthController(UserService userService) {
         this.userService = userService;
     }
 
     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody User loginUser) {
         try {
             User user = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
             return ResponseEntity.ok(user);
         } catch (UsernameNotFoundException ex) {
             return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe incorrect.");
         }
     }
 }
 