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

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour l'application de réservation.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, String>> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Gère les exceptions de type ReservationNotFoundException.
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReservationNotFound(ReservationNotFoundException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions de type ReservationConflictException.
     */
    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<Map<String, String>> handleReservationConflict(ReservationConflictException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Gère les exceptions de type InvalidApiKeyException.
     */
    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<Map<String, String>> handleInvalidApiKey(InvalidApiKeyException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Gère les autres exceptions non spécifiées.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
        return createErrorResponse("Une erreur interne est survenue.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
