package com.eventmanagement.controller;

import com.eventmanagement.dto.ApiResponse;
import com.eventmanagement.dto.EventDTO;
import com.eventmanagement.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Event REST Controller
 * Handles all HTTP requests for Event operations
 * 
 * Base URL: /api/events
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * POST /api/events - Create new event
     * @param eventDTO Event data
     * @return Created event with 201 status
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EventDTO>> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        ApiResponse<EventDTO> response = ApiResponse.success("Event created successfully", createdEvent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/events - Get all events
     * @return List of all events with 200 status
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EventDTO>>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        ApiResponse<List<EventDTO>> response = ApiResponse.success(
            "Events retrieved successfully", events
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/events/{id} - Get event by ID
     * @param id Event ID
     * @return Event details with 200 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventDTO>> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.getEventById(id);
        ApiResponse<EventDTO> response = ApiResponse.success("Event found", event);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/events/{id} - Update event
     * @param id Event ID
     * @param eventDTO Updated event data
     * @return Updated event with 200 status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventDTO>> updateEvent(
            @PathVariable Long id, 
            @Valid @RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        ApiResponse<EventDTO> response = ApiResponse.success("Event updated successfully", updatedEvent);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/events/{id} - Delete event
     * @param id Event ID
     * @return Success message with 200 status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        ApiResponse<String> response = ApiResponse.success("Event deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/events/search?name={name} - Search events by name
     * @param name Search term
     * @return List of matching events with 200 status
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EventDTO>>> searchEvents(
            @RequestParam(name = "name") String name) {
        List<EventDTO> events = eventService.searchEventsByName(name);
        ApiResponse<List<EventDTO>> response = ApiResponse.success(
            "Search completed", events
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/events/status/{status} - Filter events by status
     * @param status Event status (UPCOMING, ONGOING, COMPLETED, CANCELLED)
     * @return List of events with specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<EventDTO>>> filterByStatus(@PathVariable String status) {
        List<EventDTO> events = eventService.filterEventsByStatus(status);
        ApiResponse<List<EventDTO>> response = ApiResponse.success(
            "Events filtered by status: " + status, events
        );
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/events/{id}/register - Register participant for event
     * @param id Event ID
     * @return Updated event with 200 status
     */
    @PostMapping("/{id}/register")
    public ResponseEntity<ApiResponse<EventDTO>> registerParticipant(@PathVariable Long id) {
        EventDTO event = eventService.registerParticipant(id);
        ApiResponse<EventDTO> response = ApiResponse.success(
            "Participant registered successfully", event
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/events/available - Get events with available seats
     * @return List of events with available seats
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getAvailableEvents() {
        List<EventDTO> events = eventService.getEventsWithAvailableSeats();
        ApiResponse<List<EventDTO>> response = ApiResponse.success(
            "Available events retrieved", events
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/events/statistics - Get event statistics
     * @return Statistics data
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {
        Map<String, Object> stats = eventService.getEventStatistics();
        ApiResponse<Map<String, Object>> response = ApiResponse.success(
            "Statistics retrieved", stats
        );
        return ResponseEntity.ok(response);
    }
}
