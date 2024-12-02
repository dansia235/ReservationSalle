/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */


 package org.emiage.reservation.model;

 import javax.persistence.*;
 
 @Entity
 @Table(name = "users")
 public class User {
 
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
 
     @Column(nullable = false, unique = true)
     private String username;
 
     @Column(nullable = false, unique = true)
     private String email;
 
     @Column(nullable = false)
     private String password;
 
     // Constructeurs, getters et setters
 
     public User() {
     }
 
     public User(String username, String email, String password) {
         this.username = username;
         this.email = email;
         this.password = password;
     }
 
     // Getters et setters
 
     public Long getId() {
         return id;
     }
 
     public void setId(Long id) {
         this.id = id;
     }
 
     public String getUsername() {
         return username;
     }
 
     public void setUsername(String username) {
         this.username = username;
     }
 
     public String getEmail() {
         return email;
     }
 
     public void setEmail(String email) {
         this.email = email;
     }
 
     public String getPassword() {
         return password;
     }
 
     public void setPassword(String password) {
         this.password = password;
     }
 }
 