/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.controller;

 import org.emiage.reservation.model.Room;
 import org.emiage.reservation.model.Reservation;
 import org.emiage.reservation.service.RoomService;
 import org.emiage.reservation.service.ReservationService;
 import org.emiage.reservation.exceptions.RoomNotFoundException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 
 import java.util.List;
 
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
         Room updatedRoom = roomService.update(id, room);
         return ResponseEntity.ok(updatedRoom);
     }
 
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
         roomService.delete(id);
         return ResponseEntity.noContent().build();
     }
 
     @GetMapping
     public ResponseEntity<List<Room>> getAllRooms() {
         List<Room> rooms = roomService.findAll();
         return ResponseEntity.ok(rooms);
     }
 
     @GetMapping("/{id}")
     public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
         Room room = roomService.findById(id);
         if (room == null) {
             throw new RoomNotFoundException("Salle non trouvée avec le id " + id);
         }
         return ResponseEntity.ok(room);
     }
 
     @GetMapping("/{id}/reservations")
     public ResponseEntity<List<Reservation>> getReservationsByRoomId(@PathVariable Long id) {
         List<Reservation> reservations = reservationService.findByRoomId(id);
         return ResponseEntity.ok(reservations);
     }
 }
 