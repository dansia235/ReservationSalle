/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.controller;

 import org.emiage.reservation.dto.AuthRequest;
 import org.emiage.reservation.dto.AuthResponse;
 import org.emiage.reservation.utils.JWTUtils;
 import org.emiage.reservation.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.web.bind.annotation.*;
 
 @RestController
 public class AuthController {
 
     @Autowired
     private AuthenticationManager authenticationManager;
 
     @Autowired
     private JWTUtils jwtUtils;
 
     @Autowired
     private UserService userService;
 
     @PostMapping("/authenticate")
     public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
         );
 
         final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
         final String jwt = jwtUtils.generateToken(userDetails.getUsername());
 
         return ResponseEntity.ok(new AuthResponse(jwt));
     }
 }
 