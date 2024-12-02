/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.service;

 import org.emiage.reservation.exceptions.ReservationConflictException;
 import org.emiage.reservation.model.Reservation;
 import org.emiage.reservation.model.Room;
 import org.emiage.reservation.model.User;
 import org.emiage.reservation.repository.ReservationRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 import java.util.Date;
 import java.util.List;
 import java.util.Optional;
 
 @Service
 public class ReservationService {
 
     private final ReservationRepository reservationRepository;
 
     @Autowired
     public ReservationService(ReservationRepository reservationRepository) {
         this.reservationRepository = reservationRepository;
     }
 
     /**
      * Enregistre une nouvelle réservation après avoir vérifié la disponibilité de la salle.
      *
      * @param reservation Détails de la réservation
      * @return La réservation enregistrée
      * @throws ReservationConflictException si la salle est déjà réservée pour la période donnée
      */
     public Reservation save(Reservation reservation) {
         if (!isRoomAvailable(reservation.getRoom(), reservation.getStartDate(), reservation.getEndDate())) {
             throw new ReservationConflictException("La salle est déjà réservée pour la période sélectionnée.");
         }
         return reservationRepository.save(reservation);
     }
 
     /**
      * Recherche une réservation par son identifiant.
      *
      * @param id l'identifiant de la réservation
      * @return un Optional contenant la réservation si trouvée, sinon vide
      */
     public Optional<Reservation> findById(Long id) {
         return reservationRepository.findById(id);
     }
 
     /**
      * Récupère toutes les réservations.
      *
      * @return une liste de toutes les réservations
      */
     public List<Reservation> findAll() {
         return reservationRepository.findAll();
     }
 
     /**
      * Met à jour une réservation existante.
      *
      * @param id          l'identifiant de la réservation à mettre à jour
      * @param reservation les nouvelles informations de la réservation
      * @return la réservation mise à jour
      */
     public Reservation update(Long id, Reservation reservation) {
         Optional<Reservation> existingReservation = reservationRepository.findById(id);
         if (existingReservation.isPresent()) {
             Reservation updatedReservation = existingReservation.get();
             updatedReservation.setStartDate(reservation.getStartDate());
             updatedReservation.setEndDate(reservation.getEndDate());
             updatedReservation.setRoom(reservation.getRoom());
             updatedReservation.setUser(reservation.getUser());
             // Mettez à jour d'autres champs si nécessaire
             return reservationRepository.save(updatedReservation);
         } else {
             // Gérer le cas où la réservation n'est pas trouvée
             return null;
         }
     }
 
     /**
      * Supprime une réservation par son identifiant.
      *
      * @param id l'identifiant de la réservation à supprimer
      */
     public void delete(Long id) {
         reservationRepository.deleteById(id);
     }
 
     /**
      * Vérifie si une salle est disponible pour une période donnée.
      *
      * @param room      La salle à vérifier
      * @param startDate Date de début
      * @param endDate   Date de fin
      * @return true si la salle est disponible, sinon false
      */
     public boolean isRoomAvailable(Room room, Date startDate, Date endDate) {
         List<Reservation> reservations = reservationRepository.findByRoomIdAndStartDateBetweenOrEndDateBetween(
                 room.getId(), startDate, endDate, startDate, endDate);
         return reservations.isEmpty();
     }
 
     /**
      * Annule une réservation par son identifiant.
      *
      * @param reservationId Identifiant de la réservation
      */
     public void cancelReservation(Long reservationId) {
         reservationRepository.deleteById(reservationId);
     }
 
     /**
      * Obtient toutes les réservations d'un utilisateur spécifique.
      *
      * @param user L'utilisateur
      * @return Liste des réservations de l'utilisateur
      */
     public List<Reservation> getReservationsByUser(User user) {
         return reservationRepository.findByUserId(user.getId());
     }
 
     /**
      * Obtient toutes les réservations pour une salle spécifique.
      *
      * @param room La salle
      * @return Liste des réservations pour la salle
      */
     public List<Reservation> getReservationsByRoom(Room room) {
         return reservationRepository.findByRoomId(room.getId());
     }
 
     /**
      * Obtient toutes les réservations pour une salle spécifique par son identifiant.
      *
      * @param roomId L'identifiant de la salle
      * @return Liste des réservations pour la salle
      */
     public List<Reservation> findByRoomId(Long roomId) {
         return reservationRepository.findByRoomId(roomId);
     }
 
     /**
      * Obtient toutes les réservations entre deux dates spécifiques.
      *
      * @param startDate Date de début
      * @param endDate   Date de fin
      * @return Liste des réservations dans la période donnée
      */
     public List<Reservation> getReservationsBetweenDates(Date startDate, Date endDate) {
         return reservationRepository.findByStartDateBetween(startDate, endDate);
     }
 }
 