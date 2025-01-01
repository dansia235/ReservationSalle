/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.dto;

/**
 *
 * @author Dansia
 */


 import java.util.Date;

 public class ReservationDTO {
     private Long id;
     private Long userId;
     private Long roomId;
     private Date reservationDate;
     private String startTime;  // Format HH:mm
     private String endTime;    // Format HH:mm
     private String purpose;
     private String roomName;
     private String username;
 
     // Constructeurs
     public ReservationDTO() {}
 
     public ReservationDTO(Long id, Long userId, Long roomId, Date reservationDate, String startTime, String endTime, String purpose, String roomName, String username) {
         this.id = id;
         this.userId = userId;
         this.roomId = roomId;
         this.reservationDate = reservationDate;
         this.startTime = startTime;
         this.endTime = endTime;
         this.purpose = purpose;
         this.roomName = roomName;
         this.username = username;
     }
 
     // Getters et setters
     public Long getId() {
         return id;
     }
 
     public void setId(Long id) {
         this.id = id;
     }
 
     public Long getUserId() {
         return userId;
     }
 
     public void setUserId(Long userId) {
         this.userId = userId;
     }
 
     public Long getRoomId() {
         return roomId;
     }
 
     public void setRoomId(Long roomId) {
         this.roomId = roomId;
     }
 
     public Date getReservationDate() {
         return reservationDate;
     }
 
     public void setReservationDate(Date reservationDate) {
         this.reservationDate = reservationDate;
     }
 
     public String getStartTime() {
         return startTime;
     }
 
     public void setStartTime(String startTime) {
         this.startTime = startTime;
     }
 
     public String getEndTime() {
         return endTime;
     }
 
     public void setEndTime(String endTime) {
         this.endTime = endTime;
     }
 
     public String getPurpose() {
         return purpose;
     }
 
     public void setPurpose(String purpose) {
         this.purpose = purpose;
     }
 
     public String getRoomName() {
         return roomName;
     }
 
     public void setRoomName(String roomName) {
         this.roomName = roomName;
     }
 
     public String getUsername() {
         return username;
     }
 
     public void setUsername(String username) {
         this.username = username;
     }
 } 