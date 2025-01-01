/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.controller;

import org.emiage.reservation.dto.ReservationRequestDTO;
import org.emiage.reservation.dto.ReservationResponseDTO;
import org.emiage.reservation.exceptions.InvalidApiKeyException;
import org.emiage.reservation.exceptions.ReservationNotFoundException;
import org.emiage.reservation.model.Reservation;
import org.emiage.reservation.model.Room;
import org.emiage.reservation.model.User;
import org.emiage.reservation.service.ReservationService;
import org.emiage.reservation.service.RoomService;
import org.emiage.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, RoomService roomService, UserService userService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @RequestHeader(value="x-api-key") String apiKey,
            @RequestBody ReservationRequestDTO reservationRequestDTO) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        Room room = roomService.findById(reservationRequestDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Salle non trouvée pour l'ID fourni"));
        User user = userService.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé pour l'ID fourni"));

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setReservationDate(reservationRequestDTO.getReservationDate());
        reservation.setStartTime(reservationRequestDTO.getStartTime());
        reservation.setEndTime(reservationRequestDTO.getEndTime());
        reservation.setPurpose(reservationRequestDTO.getPurpose());

        Reservation savedReservation = reservationService.save(reservation);
        ReservationResponseDTO responseDTO = convertToResponseDto(savedReservation);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @RequestHeader(value="x-api-key") String apiKey,
            @PathVariable Long id) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        Optional<ReservationResponseDTO> reservation = reservationService.findById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseThrow(() -> new ReservationNotFoundException("Réservation non trouvée pour l'ID : " + id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations(
            @RequestHeader(value="x-api-key") String apiKey) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        List<ReservationResponseDTO> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservationsWithDetails(
            @RequestHeader(value="x-api-key") String apiKey) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        List<ReservationResponseDTO> reservations = reservationService.findAllWithDetails();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @RequestHeader(value="x-api-key") String apiKey,
            @PathVariable Long id,
            @RequestBody Reservation reservation) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        Reservation updatedReservation = reservationService.updateById(id, reservation);
        if (updatedReservation == null) {
            throw new ReservationNotFoundException("Réservation non trouvée pour l'ID : " + id);
        }
        ReservationResponseDTO responseDTO = convertToResponseDto(updatedReservation);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @RequestHeader(value="x-api-key") String apiKey,
            @PathVariable Long id) {

        if (!isValidApiKey(apiKey)) {
            throw new InvalidApiKeyException("Clé API invalide");
        }

        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isValidApiKey(String apiKey) {
        return "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A=".equals(apiKey);
    }

    private ReservationResponseDTO convertToResponseDto(Reservation reservation) {
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
