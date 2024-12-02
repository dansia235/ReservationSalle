/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.controller;

 import org.emiage.reservation.service.AvailabilityService;
 import org.emiage.reservation.model.Room;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 
 import java.util.Date;
 import java.util.List;
 import java.util.stream.Collectors;
 
 @RestController
 @RequestMapping("/api/availability")
 public class AvailabilityController {
 
     private final AvailabilityService availabilityService;
 
     @Autowired
     public AvailabilityController(AvailabilityService availabilityService) {
         this.availabilityService = availabilityService;
     }
 
     @GetMapping
     public ResponseEntity<List<Long>> getAvailableRooms(
             @RequestParam("startDate") Date startDate,
             @RequestParam("endDate") Date endDate) {
         List<Room> availableRooms = availabilityService.findAvailableRooms(startDate, endDate);
         List<Long> availableRoomIds = availableRooms.stream()
                 .map(Room::getId)
                 .collect(Collectors.toList());
         return ResponseEntity.ok(availableRoomIds);
     }
 }
 