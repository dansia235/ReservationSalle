/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.utils;

/**
 *
 * @author Dansia
 */


 import io.jsonwebtoken.Claims;
 import io.jsonwebtoken.Jwts;
 import io.jsonwebtoken.SignatureAlgorithm;
 import io.jsonwebtoken.security.Keys;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.stereotype.Component;
 
 import javax.annotation.PostConstruct;
 import javax.crypto.SecretKey;
 import java.util.Collections;
 import java.util.Date;
 
 @Component
 public class JWTUtils {
 
     @Value("${jwt.secret}")
     private String secretKey;
     private SecretKey key;
 
     @PostConstruct
     public void init() {
         this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
     }
 
     /**
      * Generates a JWT token for the given username.
      *
      * @param username the username for which the token is generated
      * @return the generated JWT token
      */
     public String generateToken(String username) {
         return Jwts.builder()
                 .setSubject(username)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiration in 1 day
                 .signWith(SignatureAlgorithm.HS256, key) // Correct method for signing
                 .compact();
     }
 
     /**
      * Extracts the username from the JWT token.
      *
      * @param token the JWT token
      * @return the username
      */
     public String extractUsername(String token) {
         Claims claims = Jwts.parser() // Use parser() instead of parserBuilder()
                 .setSigningKey(key)
                 .parseClaimsJws(token)
                 .getBody();
         return claims.getSubject();
     }
 
     /**
      * Validates the given JWT token.
      *
      * @param token the JWT token to validate
      * @return true if the token is valid, false otherwise
      */
     public boolean validateToken(String token) {
         try {
             Jwts.parser() // Use parser() instead of parserBuilder()
                 .setSigningKey(key)
                 .parseClaimsJws(token);
             return true;
         } catch (Exception e) {
             return false;
         }
     }
 
     /**
      * Retrieves authentication information from the JWT token.
      *
      * @param token the JWT token
      * @return the authentication object containing user details
      */
     public Authentication getAuthentication(String token) {
         Claims claims = Jwts.parser() // Use parser() instead of parserBuilder()
                 .setSigningKey(key)
                 .parseClaimsJws(token)
                 .getBody();
         String username = claims.getSubject();
         String role = claims.get("role", String.class);
         SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
         return new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
     }
 } 