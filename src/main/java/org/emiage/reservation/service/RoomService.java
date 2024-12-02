/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.service;

/**
 *
 * @author Dansia
 */

 import org.emiage.reservation.exceptions.ResourceNotFoundException;
 import org.emiage.reservation.model.Room;
 import org.emiage.reservation.repository.RoomRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 import java.util.List;
 
 @Service
 public class RoomService {
 
     private final RoomRepository roomRepository;
 
     @Autowired
     public RoomService(RoomRepository roomRepository) {
         this.roomRepository = roomRepository;
     }
 
     public Room save(Room room) {
         return roomRepository.save(room);
     }
 
     public Room update(Long id, Room room) {
         return roomRepository.findById(id)
                 .map(existingRoom -> {
                     existingRoom.setName(room.getName());
                     existingRoom.setCapacity(room.getCapacity());
                     existingRoom.setLocation(room.getLocation());
                     // Mettez à jour d'autres champs si nécessaire
                     return roomRepository.save(existingRoom);
                 })
                 .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée pour l'id :: " + id));
     }
 
     public void delete(Long id) {
         Room room = roomRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée pour l'id :: " + id));
         roomRepository.delete(room);
     }
 
     public List<Room> findAll() {
         return roomRepository.findAll();
     }
 
     public Room findById(Long id) {
         return roomRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée pour l'id :: " + id));
     }
 }