/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dansia
 */

 package org.emiage.reservation.config;

 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Primary;
 import org.springframework.orm.jpa.JpaTransactionManager;
 import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
 import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 import org.springframework.transaction.annotation.EnableTransactionManagement;
 
 import javax.sql.DataSource;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.jdbc.datasource.DriverManagerDataSource;
 import javax.persistence.EntityManagerFactory;
 import java.util.HashMap;
 import java.util.Map;
 
 @Configuration
 @EnableJpaRepositories(basePackages = "org.emiage.reservation.repository")
 @EnableTransactionManagement
 public class DatabaseConfig {
 
     @Value("${spring.datasource.url}")
     private String url;
 
     @Value("${spring.datasource.username}")
     private String username;
 
     @Value("${spring.datasource.password}")
     private String password;
 
     @Value("${spring.datasource.driver-class-name}")
     private String driverClassName;
 
     @Bean
     @Primary
     public DataSource dataSource() {
         DriverManagerDataSource dataSource = new DriverManagerDataSource();
         dataSource.setDriverClassName(driverClassName);
         dataSource.setUrl(url);
         dataSource.setUsername(username);
         dataSource.setPassword(password);
         return dataSource;
     }
 
     @Bean
     public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
         em.setDataSource(dataSource);
         em.setPackagesToScan("org.emiage.reservation.model");
 
         Map<String, Object> properties = new HashMap<>();
         properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
         properties.put("hibernate.hbm2ddl.auto", "update");
         properties.put("hibernate.show_sql", "true");
         em.setJpaPropertyMap(properties);
 
         em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
 
         return em;
     }
 
     @Bean
     public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
         JpaTransactionManager transactionManager = new JpaTransactionManager();
         transactionManager.setEntityManagerFactory(entityManagerFactory);
         return transactionManager;
     }
 }
 
 
 