package com.eventmanagement.console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Console-based Event Management System
 * Demonstrates core Java concepts: Classes, Objects, Collections, CRUD operations
 */
public class EventManagementConsole {
    
    // Event Class
    static class Event {
        private int eventId;
        private String eventName;
        private String eventDescription;
        private LocalDate eventDate;
        private String eventLocation;
        private int maxParticipants;
        private int registeredCount;
        private String eventStatus;
        private LocalDateTime createdDatetime;
        
        // Constructor
        public Event(int eventId, String eventName, String eventDescription, 
                    LocalDate eventDate, String eventLocation, int maxParticipants) {
            this.eventId = eventId;
            this.eventName = eventName;
            this.eventDescription = eventDescription;
            this.eventDate = eventDate;
            this.eventLocation = eventLocation;
            this.maxParticipants = maxParticipants;
            this.registeredCount = 0;
            this.eventStatus = "UPCOMING";
            this.createdDatetime = LocalDateTime.now();
        }
        
        // Getters and Setters
        public int getEventId() { return eventId; }
        public void setEventId(int eventId) { this.eventId = eventId; }
        
        public String getEventName() { return eventName; }
        public void setEventName(String eventName) { this.eventName = eventName; }
        
        public String getEventDescription() { return eventDescription; }
        public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
        
        public LocalDate getEventDate() { return eventDate; }
        public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
        
        public String getEventLocation() { return eventLocation; }
        public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
        
        public int getMaxParticipants() { return maxParticipants; }
        public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
        
        public int getRegisteredCount() { return registeredCount; }
        public void setRegisteredCount(int registeredCount) { this.registeredCount = registeredCount; }
        
        public String getEventStatus() { return eventStatus; }
        public void setEventStatus(String eventStatus) { this.eventStatus = eventStatus; }
        
        public LocalDateTime getCreatedDatetime() { return createdDatetime; }
        
        @Override
        public String toString() {
            return String.format(
                "\n--- Event Details ---\n" +
                "ID: %d\n" +
                "Name: %s\n" +
                "Description: %s\n" +
                "Date: %s\n" +
                "Location: %s\n" +
                "Participants: %d/%d\n" +
                "Status: %s\n" +
                "Created: %s\n" +
                "-------------------",
                eventId, eventName, eventDescription, eventDate, eventLocation,
                registeredCount, maxParticipants, eventStatus, 
                createdDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }
    }
    
    // Event Management System
    private static HashMap<Integer, Event> eventMap = new HashMap<>();
    private static int nextEventId = 1;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   EVENT MANAGEMENT SYSTEM - CONSOLE");
        System.out.println("===========================================\n");
        
        // Add sample data
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addEvent();
                    break;
                case 2:
                    viewAllEvents();
                    break;
                case 3:
                    viewEventById();
                    break;
                case 4:
                    updateEvent();
                    break;
                case 5:
                    deleteEvent();
                    break;
                case 6:
                    searchEventByName();
                    break;
                case 7:
                    registerParticipant();
                    break;
                case 8:
                    filterByStatus();
                    break;
                case 9:
                    running = false;
                    System.out.println("\nThank you for using Event Management System!");
                    break;
                default:
                    System.out.println("\n❌ Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Event");
        System.out.println("2. View All Events");
        System.out.println("3. View Event by ID");
        System.out.println("4. Update Event");
        System.out.println("5. Delete Event");
        System.out.println("6. Search Event by Name");
        System.out.println("7. Register Participant");
        System.out.println("8. Filter Events by Status");
        System.out.println("9. Exit");
        System.out.println("===============================");
    }
    
    private static void addEvent() {
        System.out.println("\n--- Add New Event ---");
        
        scanner.nextLine(); // Clear buffer
        System.out.print("Event Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Event Description: ");
        String description = scanner.nextLine();
        
        System.out.print("Event Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);
        
        System.out.print("Event Location: ");
        String location = scanner.nextLine();
        
        int maxParticipants = getIntInput("Max Participants: ");
        
        Event event = new Event(nextEventId++, name, description, date, location, maxParticipants);
        eventMap.put(event.getEventId(), event);
        
        System.out.println("\n✅ Event added successfully! Event ID: " + event.getEventId());
    }
    
    private static void viewAllEvents() {
        System.out.println("\n========== ALL EVENTS ==========");
        
        if (eventMap.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        
        ArrayList<Event> eventList = new ArrayList<>(eventMap.values());
        for (Event event : eventList) {
            System.out.println(event);
        }
        System.out.println("\nTotal Events: " + eventList.size());
    }
    
    private static void viewEventById() {
        int id = getIntInput("\nEnter Event ID: ");
        
        Event event = eventMap.get(id);
        if (event != null) {
            System.out.println(event);
        } else {
            System.out.println("\n❌ Event not found with ID: " + id);
        }
    }
    
    private static void updateEvent() {
        int id = getIntInput("\nEnter Event ID to update: ");
        
        Event event = eventMap.get(id);
        if (event == null) {
            System.out.println("\n❌ Event not found with ID: " + id);
            return;
        }
        
        System.out.println("\nCurrent Event Details:" + event);
        System.out.println("\n--- Update Event (press Enter to skip) ---");
        scanner.nextLine(); // Clear buffer
        
        System.out.print("New Event Name [" + event.getEventName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) event.setEventName(name);
        
        System.out.print("New Description [" + event.getEventDescription() + "]: ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) event.setEventDescription(description);
        
        System.out.print("New Location [" + event.getEventLocation() + "]: ");
        String location = scanner.nextLine();
        if (!location.isEmpty()) event.setEventLocation(location);
        
        System.out.print("New Status (UPCOMING/ONGOING/COMPLETED/CANCELLED) [" + event.getEventStatus() + "]: ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) event.setEventStatus(status.toUpperCase());
        
        System.out.println("\n✅ Event updated successfully!");
    }
    
    private static void deleteEvent() {
        int id = getIntInput("\nEnter Event ID to delete: ");
        
        Event event = eventMap.remove(id);
        if (event != null) {
            System.out.println("\n✅ Event deleted successfully!");
            System.out.println("Deleted: " + event.getEventName());
        } else {
            System.out.println("\n❌ Event not found with ID: " + id);
        }
    }
    
    private static void searchEventByName() {
        scanner.nextLine(); // Clear buffer
        System.out.print("\nEnter event name to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        System.out.println("\n========== SEARCH RESULTS ==========");
        ArrayList<Event> results = new ArrayList<>();
        
        for (Event event : eventMap.values()) {
            if (event.getEventName().toLowerCase().contains(searchTerm)) {
                results.add(event);
                System.out.println(event);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No events found matching: " + searchTerm);
        } else {
            System.out.println("\nFound " + results.size() + " event(s)");
        }
    }
    
    private static void registerParticipant() {
        int id = getIntInput("\nEnter Event ID to register: ");
        
        Event event = eventMap.get(id);
        if (event == null) {
            System.out.println("\n❌ Event not found with ID: " + id);
            return;
        }
        
        if (event.getRegisteredCount() >= event.getMaxParticipants()) {
            System.out.println("\n❌ Event is full! Cannot register more participants.");
            System.out.println("Current: " + event.getRegisteredCount() + "/" + event.getMaxParticipants());
            return;
        }
        
        event.setRegisteredCount(event.getRegisteredCount() + 1);
        System.out.println("\n✅ Participant registered successfully!");
        System.out.println("Event: " + event.getEventName());
        System.out.println("Registered: " + event.getRegisteredCount() + "/" + event.getMaxParticipants());
    }
    
    private static void filterByStatus() {
        scanner.nextLine(); // Clear buffer
        System.out.print("\nEnter status (UPCOMING/ONGOING/COMPLETED/CANCELLED): ");
        String status = scanner.nextLine().toUpperCase();
        
        System.out.println("\n========== EVENTS WITH STATUS: " + status + " ==========");
        ArrayList<Event> results = new ArrayList<>();
        
        for (Event event : eventMap.values()) {
            if (event.getEventStatus().equals(status)) {
                results.add(event);
                System.out.println(event);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No events found with status: " + status);
        } else {
            System.out.println("\nFound " + results.size() + " event(s)");
        }
    }
    
    private static void initializeSampleData() {
        Event e1 = new Event(nextEventId++, "Tech Conference 2026", 
            "Annual technology conference", LocalDate.of(2026, 4, 15), 
            "Convention Center, New York", 500);
        e1.setRegisteredCount(245);
        eventMap.put(e1.getEventId(), e1);
        
        Event e2 = new Event(nextEventId++, "Spring Boot Workshop", 
            "Hands-on Spring Boot development", LocalDate.of(2026, 3, 20), 
            "Tech Hub, San Francisco", 50);
        e2.setRegisteredCount(50);
        eventMap.put(e2.getEventId(), e2);
        
        Event e3 = new Event(nextEventId++, "AI Summit", 
            "Artificial Intelligence summit", LocalDate.of(2026, 5, 10), 
            "Innovation Center, Boston", 300);
        e3.setRegisteredCount(150);
        eventMap.put(e3.getEventId(), e3);
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        return scanner.nextInt();
    }
}
