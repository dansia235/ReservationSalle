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
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.query.Param;
 
 import java.util.Date;
 import java.util.List;
 import java.util.Optional;
 
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
      * Recherche les réservations dont la date de début ou de fin est entre startTime et endTime.
      *
      * @param roomId    l'identifiant de la salle
      * @param startTime la date de début de l'intervalle
      * @param endTime   la date de fin de l'intervalle
      * @return une liste de réservations correspondant aux critères
      */
     @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND (r.startTime BETWEEN :startTime AND :endTime OR r.endTime BETWEEN :startTime AND :endTime)")
     List<Reservation> findByRoomIdAndTimeRange(
             @Param("roomId") Long roomId,
             @Param("startTime") Date startTime,
             @Param("endTime") Date endTime
     );
 
     /**
      * Requête personnalisée pour récupérer toutes les réservations avec des détails utilisateur et salle.
      *
      * @return une liste contenant les informations détaillées des réservations
      */
     @Query("SELECT r FROM Reservation r JOIN FETCH r.room rm JOIN FETCH r.user u")
     List<Reservation> findAllWithDetails();
 
     /**
      * Requête personnalisée pour récupérer une réservation avec des détails utilisateur et salle par ID.
      *
      * @param id l'identifiant de la réservation
      * @return une réservation avec ses détails
      */
     @Query("SELECT r FROM Reservation r JOIN FETCH r.room rm JOIN FETCH r.user u WHERE r.id = :id")
     Optional<Reservation> findByIdWithDetails(@Param("id") Long id);
 }
 