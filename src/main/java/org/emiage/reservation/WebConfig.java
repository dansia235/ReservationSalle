/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation;

/**
 *
 * @author Dansia
 */




 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 import org.springframework.lang.NonNull;
 
 @Configuration
 public class WebConfig implements WebMvcConfigurer {
 
     @Override
     public void addCorsMappings(@NonNull CorsRegistry registry) {
         registry.addMapping("/**")
                 .allowedOrigins("http://127.0.0.1:5500")
                 .allowedMethods("GET", "POST", "PUT", "DELETE")
                 .allowedHeaders("*")
                 .allowCredentials(true);
     }
 }
 