/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.exceptions;

/**
 *
 * @author Dansia
 */
/**
 * Exception levée en cas de conflit lors d'une réservation.
 */
public class ReservationConflictException extends RuntimeException {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le détail du message
     */
    public ReservationConflictException(String message) {
        super(message);
    }
}