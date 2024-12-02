/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.repository;

/**
 *
 * @author Dansia
 */

 import org.emiage.reservation.model.Reservation;
 import org.springframework.data.jpa.repository.JpaRepository;
 import java.util.Date;
 import java.util.List;
 
 /**
  * Dépôt pour l'entité Reservation.
  */
 public interface ReservationRepository extends JpaRepository<Reservation, Long> {
 
     /**
      * Recherche les réservations par identifiant d'utilisateur.
      *
      * @param userId l'identifiant de l'utilisateur
      * @return une liste de réservations associées à l'utilisateur
      */
     List<Reservation> findByUserId(Long userId);
 
     /**
      * Recherche les réservations par identifiant de salle.
      *
      * @param roomId l'identifiant de la salle
      * @return une liste de réservations associées à la salle
      */
     List<Reservation> findByRoomId(Long roomId);
 
     /**
      * Recherche les réservations dont la date de début est entre startDate et endDate.
      *
      * @param startDate la date de début de l'intervalle
      * @param endDate   la date de fin de l'intervalle
      * @return une liste de réservations correspondant aux critères
      */
     List<Reservation> findByStartDateBetween(Date startDate, Date endDate);
 
     /**
      * Recherche les réservations dont la date de fin est entre startDate et endDate.
      *
      * @param startDate la date de début de l'intervalle
      * @param endDate   la date de fin de l'intervalle
      * @return une liste de réservations correspondant aux critères
      */
     List<Reservation> findByEndDateBetween(Date startDate, Date endDate);
 
     /**
      * Recherche les réservations pour une salle spécifique dont la date de début ou de fin est entre startDate et endDate.
      *
      * @param roomId    l'identifiant de la salle
      * @param startDate la date de début de l'intervalle
      * @param endDate   la date de fin de l'intervalle
      * @return une liste de réservations correspondant aux critères
      */
     List<Reservation> findByRoomIdAndStartDateBetweenOrEndDateBetween(
             Long roomId, Date startDate, Date endDate, Date startDate2, Date endDate2);
 }