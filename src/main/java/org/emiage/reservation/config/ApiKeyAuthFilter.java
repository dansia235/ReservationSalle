/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.config;


 import org.springframework.lang.NonNull;
 import org.springframework.web.filter.OncePerRequestFilter;
 
 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 
 public class ApiKeyAuthFilter extends OncePerRequestFilter {
 
     private static final String API_KEY_HEADER_NAME = "x-api-key";
     private final String expectedApiKey;
 
     public ApiKeyAuthFilter(String expectedApiKey) {
         this.expectedApiKey = expectedApiKey;
     }
 
     @Override
     protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
             throws ServletException, IOException {
         String requestApiKey = request.getHeader(API_KEY_HEADER_NAME);
         if (expectedApiKey.equals(requestApiKey)) {
             filterChain.doFilter(request, response);
         } else {
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         }
     }
 }
 