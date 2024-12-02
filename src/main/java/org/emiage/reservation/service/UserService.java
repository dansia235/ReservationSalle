/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */


 package org.emiage.reservation.service;

 import org.emiage.reservation.model.User;
 import org.emiage.reservation.repository.UserRepository;
 import org.emiage.reservation.exceptions.UserNotFoundException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Service;
 
 import java.util.Collections;
 import java.util.List;
 import java.util.Optional;
 
 @Service
 public class UserService implements UserDetailsService {
 
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
 
     @Autowired
     public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
         this.userRepository = userRepository;
         this.passwordEncoder = passwordEncoder;
     }
 
     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
 
         return new org.springframework.security.core.userdetails.User(
             user.getUsername(),
             user.getPassword(),
             Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
         );
     }
 
     public User save(User user) {
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository.save(user);
     }
 
     public User findByUsername(String username) throws UsernameNotFoundException {
         return userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
     }
 
     public List<User> findAll() {
         return userRepository.findAll();
     }
 
     public User update(Long id, User user) {
         Optional<User> existingUser = userRepository.findById(id);
         if (existingUser.isPresent()) {
             User updatedUser = existingUser.get();
             updatedUser.setUsername(user.getUsername());
             updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
             updatedUser.setEmail(user.getEmail());
             // Mise à jour d'autres champs
             return userRepository.save(updatedUser);
         } else {
             throw new UserNotFoundException("Utilisateur non trouvé avec le id " + id);
         }
     }
 
     public void delete(Long id) {
         userRepository.deleteById(id);
     }
 
     public Optional<User> findById(Long id) {
         return userRepository.findById(id);
     }
 }
 