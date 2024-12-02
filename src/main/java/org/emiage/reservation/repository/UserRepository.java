/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.repository;

/**
 *
 * @author Dansia
 */

 import org.emiage.reservation.model.User;
 import org.springframework.data.jpa.repository.JpaRepository;
 import java.util.Optional;

/**
 * Dépôt pour l'entité User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur
     * @return un Optional contenant l'utilisateur s'il est trouvé, sinon vide
     */
    Optional<User> findByUsername(String username);

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @param email l'adresse e-mail
     * @return un Optional contenant l'utilisateur s'il est trouvé, sinon vide
     */
    Optional<User> findByEmail(String email);
}
