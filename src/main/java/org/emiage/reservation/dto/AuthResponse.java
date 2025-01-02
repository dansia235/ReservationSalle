/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.dto;

/**
 *
 * @author Dansia
 */



public class AuthResponse {
     private final String jwt;
 
     public AuthResponse(String jwt) {
         this.jwt = jwt;
     }
 
     public String getJwt() {
         return jwt;
     }
 }
 