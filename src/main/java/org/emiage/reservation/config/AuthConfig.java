/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.config;

/**
 *
 * @author Dansia
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Value("${api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
