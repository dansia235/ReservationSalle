/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.security;

/**
 *
 * @author Dansia
 */



 import org.emiage.reservation.utils.JWTUtils;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
 import org.springframework.stereotype.Component;
 import org.springframework.web.filter.OncePerRequestFilter;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.emiage.reservation.service.UserService;
 
 import io.jsonwebtoken.ExpiredJwtException;
 
 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.lang.NonNull;
 
 @Component
 public class JwtRequestFilter extends OncePerRequestFilter {
 
     private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
 
     private final JWTUtils jwtUtils;
     private final UserService userService;
 
     public JwtRequestFilter(JWTUtils jwtUtils, UserService userService) {
         this.jwtUtils = jwtUtils;
         this.userService = userService;
     }
 
     @Override
     protected void doFilterInternal(
             @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain chain)
             throws ServletException, IOException {
         final String requestTokenHeader = request.getHeader("Authorization");
 
         String username = null;
         String jwtToken = null;
 
         if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
             jwtToken = requestTokenHeader.substring(7);
             try {
                 username = jwtUtils.extractUsername(jwtToken);
             } catch (IllegalArgumentException e) {
                 logger.error("Unable to extract JWT Token", e);
             } catch (ExpiredJwtException e) {
                 logger.warn("JWT Token has expired", e);
             }
         } else {
             logger.debug("JWT Token does not begin with Bearer or is null");
         }
 
         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             UserDetails userDetails = this.userService.loadUserByUsername(username);
 
             if (jwtUtils.validateToken(jwtToken)) {
                 UsernamePasswordAuthenticationToken authenticationToken =
                         new UsernamePasswordAuthenticationToken(
                                 userDetails, null, userDetails.getAuthorities());
                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             }
         }
 
         chain.doFilter(request, response);
     }
 }
 