package com.eventmanagement.service;

import com.eventmanagement.dto.EventDTO;
import java.util.List;
import java.util.Map;

/**
 * Event Service Interface
 * Defines business logic operations for Event management
 */
public interface EventService {

    /**
     * Create a new event
     */
    EventDTO createEvent(EventDTO eventDTO);

    /**
     * Get all events
     */
    List<EventDTO> getAllEvents();

    /**
     * Get event by ID
     */
    EventDTO getEventById(Long eventId);

    /**
     * Update existing event
     */
    EventDTO updateEvent(Long eventId, EventDTO eventDTO);

    /**
     * Delete event by ID
     */
    void deleteEvent(Long eventId);

    /**
     * Search events by name
     */
    List<EventDTO> searchEventsByName(String eventName);

    /**
     * Filter events by status
     */
    List<EventDTO> filterEventsByStatus(String status);

    /**
     * Register participant for an event
     */
    EventDTO registerParticipant(Long eventId);

    /**
     * Get events with available seats
     */
    List<EventDTO> getEventsWithAvailableSeats();

    /**
     * Get event statistics
     */
    Map<String, Object> getEventStatistics();
}
