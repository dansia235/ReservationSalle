/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.config;

 import org.emiage.reservation.utils.JWTUtils;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.web.filter.OncePerRequestFilter;
 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import org.springframework.lang.NonNull;
 
 /**
  * Filtre d'authentification JWT pour vérifier les jetons JWT dans les requêtes HTTP.
  */
 public class JWTAuthenticationFilter extends OncePerRequestFilter {
     private final JWTUtils jwtUtils;
 
     /**
      * Constructeur pour initialiser le filtre avec l'utilitaire JWT.
      *
      * @param jwtUtils l'utilitaire JWT pour valider et extraire les informations du jeton
      */
     public JWTAuthenticationFilter(JWTUtils jwtUtils) {
         this.jwtUtils = jwtUtils;
     }
 
     /**
      * Méthode pour filtrer les requêtes HTTP et vérifier les jetons JWT.
      *
      * @param request la requête HTTP
      * @param response la réponse HTTP
      * @param filterChain la chaîne de filtres
      * @throws ServletException si une erreur de servlet se produit
      * @throws IOException si une erreur d'entrée/sortie se produit
      */
     @Override
     protected void doFilterInternal(
             @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain) throws ServletException, IOException {
         String authHeader = request.getHeader("Authorization");
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
             String jwt = authHeader.substring(7);
             if (jwtUtils.validateToken(jwt)) {
                 Authentication authentication = jwtUtils.getAuthentication(jwt);
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
         }
         filterChain.doFilter(request, response);
     }
 }
 