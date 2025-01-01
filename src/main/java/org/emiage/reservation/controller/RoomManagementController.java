/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.controller;

import org.emiage.reservation.dto.ReservationResponseDTO;
import org.emiage.reservation.exceptions.RoomNotFoundException;
import org.emiage.reservation.model.Room;
import org.emiage.reservation.service.RoomService;
import org.emiage.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomManagementController {

    private final RoomService roomService;
    private final ReservationService reservationService;

    @Autowired
    public RoomManagementController(RoomService roomService, ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room savedRoom = roomService.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            Room updatedRoom = roomService.update(id, room);
            return ResponseEntity.ok(updatedRoom);
        } catch (RoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (roomOptional.isPresent()) {
            return ResponseEntity.ok(roomOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByRoomId(@PathVariable Long id) {
        List<ReservationResponseDTO> reservations = reservationService.findByRoomId(id);
        return ResponseEntity.ok(reservations);
    }
}
