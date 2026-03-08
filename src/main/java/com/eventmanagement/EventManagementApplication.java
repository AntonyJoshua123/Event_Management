package com.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class for Event Management System
 * 
 * @author Event Management Team
 * @version 1.0
 */
@SpringBootApplication
public class EventManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("  Event Management System Started!");
        System.out.println("  Access at: http://localhost:8080");
        System.out.println("===========================================\n");
    }
}
