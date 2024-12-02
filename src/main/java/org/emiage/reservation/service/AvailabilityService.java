/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.service;

/**
 *
 * @author Dansia
 */

 import org.emiage.reservation.model.Room;
import org.emiage.reservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final RoomRepository roomRepository;
    private final ReservationService reservationService;

    @Autowired
    public AvailabilityService(RoomRepository roomRepository, ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
    }

    /**
     * Obtient la liste des salles disponibles pour une période donnée.
     *
     * @param startDate Date de début de la période
     * @param endDate   Date de fin de la période
     * @return Liste des salles disponibles
     */
    public List<Room> findAvailableRooms(Date startDate, Date endDate) {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> reservationService.isRoomAvailable(room, startDate, endDate))
                .collect(Collectors.toList());
    }
}
