/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.reservation.config;

/**
 *
 * @author Dansia
 */
import javax.annotation.PostConstruct;
import javax.naming.Context;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JndiConfig {

    @PostConstruct
    public void init() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
    }
}

