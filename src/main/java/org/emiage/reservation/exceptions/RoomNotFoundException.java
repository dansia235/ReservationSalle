/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.exceptions;

/**
 *
 * @author Dansia
 */
public class RoomNotFoundException extends RuntimeException 
{ 
    public RoomNotFoundException(String message) 
    { super(message); 
    } 
}