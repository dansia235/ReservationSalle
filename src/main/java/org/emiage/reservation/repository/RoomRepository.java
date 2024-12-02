/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.repository;

/**
 *
 * @author Dansia
 */


 import org.emiage.reservation.model.Room;
 import org.springframework.data.jpa.repository.JpaRepository;
 import java.util.List;

/**
 * Dépôt pour l'entité Room.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
    /**
     * Recherche les salles par leur capacité minimale.
     *
     * @param capacity la capacité minimale
     * @return une liste de salles correspondant au critère
     */
    List<Room> findByCapacityGreaterThanEqual(int capacity);

    /**
     * Recherche les salles par leur localisation.
     *
     * @param location la localisation
     * @return une liste de salles situées à l'emplacement spécifié
     */
    List<Room> findByLocation(String location);
}