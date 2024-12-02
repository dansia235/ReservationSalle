/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.utils;

/**
 *
 * @author Dansia
 */

 import java.util.regex.Pattern;

 /**
  * Classe utilitaire pour la validation des données d'entrée.
  */
 public class ValidationUtils {
 
     // Expression régulière pour valider les adresses e-mail
     private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
     private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
 
     /**
      * Vérifie si une adresse e-mail est valide.
      *
      * @param email l'adresse e-mail à valider
      * @return true si l'adresse e-mail est valide, sinon false
      */
     public static boolean isValidEmail(String email) {
         return email != null && EMAIL_PATTERN.matcher(email).matches();
     }
 }