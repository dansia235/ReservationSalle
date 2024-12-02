/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation;

/**
 *
 * @author Dansia
 */


 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.autoconfigure.domain.EntityScan;
 import org.springframework.context.annotation.ComponentScan;
 
 @SpringBootApplication
 @EntityScan(basePackages = "org.emiage.reservation.model")
 @ComponentScan(basePackages = "org.emiage.reservation")
 public class ReservationApplication {
 
     public static void main(String[] args) {
         SpringApplication.run(ReservationApplication.class, args);
     }
 }
 
 