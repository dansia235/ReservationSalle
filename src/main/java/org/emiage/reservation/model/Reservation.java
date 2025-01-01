/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.model;

 import javax.persistence.*;
 import java.util.Date;
 
 @Entity
 @Table(name = "reservations")
 public class Reservation {
 
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
 
     @Column(nullable = false)
     @Temporal(TemporalType.DATE)
     private Date reservationDate;
 
     @Column(nullable = false)
     @Temporal(TemporalType.TIME)
     private Date startTime;
 
     @Column(nullable = false)
     @Temporal(TemporalType.TIME)
     private Date endTime;
 
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "room_id", nullable = false)
     private Room room;
 
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     private User user;
 
     @Column(nullable = false, length = 500)
     private String purpose;
 
     // Constructeurs
     public Reservation() {}
 
     public Reservation(Long id, Date reservationDate, Date startTime, Date endTime, Room room, User user, String purpose) {
         this.id = id;
         this.reservationDate = reservationDate;
         this.startTime = startTime;
         this.endTime = endTime;
         this.room = room;
         this.user = user;
         this.purpose = purpose;
     }
 
     // Getters et setters
     public Long getId() {
         return id;
     }
 
     public void setId(Long id) {
         this.id = id;
     }
 
     public Date getReservationDate() {
         return reservationDate;
     }
 
     public void setReservationDate(Date reservationDate) {
         this.reservationDate = reservationDate;
     }
 
     public Date getStartTime() {
         return startTime;
     }
 
     public void setStartTime(Date startTime) {
         this.startTime = startTime;
     }
 
     public Date getEndTime() {
         return endTime;
     }
 
     public void setEndTime(Date endTime) {
         this.endTime = endTime;
     }
 
     public Room getRoom() {
         return room;
     }
 
     public void setRoom(Room room) {
         this.room = room;
     }
 
     public User getUser() {
         return user;
     }
 
     public void setUser(User user) {
         this.user = user;
     }
 
     public String getPurpose() {
         return purpose;
     }
 
     public void setPurpose(String purpose) {
         this.purpose = purpose;
     }
 } 