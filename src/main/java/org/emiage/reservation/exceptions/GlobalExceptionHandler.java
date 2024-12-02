/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.exceptions;

/**
 *
 * @author Dansia
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Gestionnaire global des exceptions pour l'application de réservation.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type ReservationConflictException.
     *
     * @param ex      l'exception levée
     * @param request les détails de la requête
     * @return une réponse HTTP avec le statut CONFLICT (409)
     */
    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<String> handleReservationConflictException(
            ReservationConflictException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Gère les exceptions de type ReservationNotFoundException.
     *
     * @param ex      l'exception levée
     * @param request les détails de la requête
     * @return une réponse HTTP avec le statut NOT_FOUND (404)
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(
            ReservationNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions de type RoomNotFoundException.
     *
     * @param ex      l'exception levée
     * @param request les détails de la requête
     * @return une réponse HTTP avec le statut NOT_FOUND (404)
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFoundException(
            RoomNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions de type UserNotAuthorizedException.
     *
     * @param ex      l'exception levée
     * @param request les détails de la requête
     * @return une réponse HTTP avec le statut FORBIDDEN (403)
     */
    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<String> handleUserNotAuthorizedException(
            UserNotAuthorizedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Gère les autres exceptions non spécifiées.
     *
     * @param ex      l'exception levée
     * @param request les détails de la requête
     * @return une réponse HTTP avec le statut INTERNAL_SERVER_ERROR (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
