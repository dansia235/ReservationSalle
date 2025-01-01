/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.service;

 import org.emiage.reservation.dto.ReservationResponseDTO;
 import org.emiage.reservation.model.Reservation;
 import org.emiage.reservation.model.Room;
 import org.emiage.reservation.repository.ReservationRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.List;
 import java.util.Optional;
 import java.util.stream.Collectors;
 
 @Service
 public class ReservationService {
 
     private final ReservationRepository reservationRepository;
 
     @Autowired
     public ReservationService(ReservationRepository reservationRepository) {
         this.reservationRepository = reservationRepository;
     }
 
     public Reservation save(Reservation reservation) {
         return reservationRepository.save(reservation);
     }
 
     public Optional<ReservationResponseDTO> findById(Long id) {
         Optional<Reservation> reservationOpt = reservationRepository.findById(id);
         return reservationOpt.map(this::convertToDto);
     }
 
     public List<ReservationResponseDTO> findAll() {
         List<Reservation> reservations = reservationRepository.findAll();
         return reservations.stream()
                 .map(this::convertToDto)
                 .collect(Collectors.toList());
     }
 
     public List<ReservationResponseDTO> findAllWithDetails() {
         List<Reservation> reservations = reservationRepository.findAllWithDetails();
         return reservations.stream()
                 .map(this::convertToDto)
                 .collect(Collectors.toList());
     }
 
     public List<ReservationResponseDTO> findByRoomId(Long roomId) {
         List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
         return reservations.stream()
                 .map(this::convertToDto)
                 .collect(Collectors.toList());
     }
 
     public Reservation updateById(Long id, Reservation reservation) {
         Optional<Reservation> existingReservation = reservationRepository.findById(id);
         if (existingReservation.isPresent()) {
             Reservation updatedReservation = existingReservation.get();
             updatedReservation.setReservationDate(reservation.getReservationDate());
             updatedReservation.setStartTime(reservation.getStartTime());
             updatedReservation.setEndTime(reservation.getEndTime());
             updatedReservation.setRoom(reservation.getRoom());
             updatedReservation.setUser(reservation.getUser());
             updatedReservation.setPurpose(reservation.getPurpose());
             return reservationRepository.save(updatedReservation);
         } else {
             throw new IllegalArgumentException("Réservation non trouvée pour l'ID fourni");
         }
     }
 
     public void deleteById(Long id) {
         Optional<Reservation> reservation = reservationRepository.findById(id);
         reservation.ifPresent(reservationRepository::delete);
     }
 
     /**
      * Vérifie si une salle est disponible pour une période donnée.
      *
      * @param room      La salle à vérifier
      * @param startTime La date et l'heure de début
      * @param endTime   La date et l'heure de fin
      * @return true si la salle est disponible, sinon false
      */
     public boolean isRoomAvailable(Room room, Date startTime, Date endTime) {
         List<Reservation> reservations = reservationRepository.findByRoomIdAndTimeRange(room.getId(), startTime, endTime);
         return reservations.isEmpty();
     }
 
     private ReservationResponseDTO convertToDto(Reservation reservation) {
         ReservationResponseDTO dto = new ReservationResponseDTO();
         dto.setId(reservation.getId());
         dto.setReservationDate(formatDate(reservation.getReservationDate()));
         dto.setStartTime(formatTime(reservation.getStartTime()));
         dto.setEndTime(formatTime(reservation.getEndTime()));
         dto.setRoomName(reservation.getRoom().getName());
         dto.setUsername(reservation.getUser().getUsername());
         dto.setPurpose(reservation.getPurpose());
         return dto;
     }
 
     private String formatDate(Date date) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         return sdf.format(date);
     }
 
     private String formatTime(Date time) {
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         return sdf.format(time);
     }
 } 