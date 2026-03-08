package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Event Repository Interface
 * Provides CRUD operations and custom queries for Event entity
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find events by name containing search term (case-insensitive)
     */
    List<Event> findByEventNameContainingIgnoreCase(String eventName);

    /**
     * Find events by status
     */
    List<Event> findByEventStatus(String eventStatus);

    /**
     * Find events by status ordered by date
     */
    List<Event> findByEventStatusOrderByEventDateAsc(String eventStatus);

    /**
     * Custom query to find events with available seats
     */
    @Query("SELECT e FROM Event e WHERE e.registeredCount < e.maxParticipants")
    List<Event> findEventsWithAvailableSeats();

    /**
     * Custom query to find full events
     */
    @Query("SELECT e FROM Event e WHERE e.registeredCount >= e.maxParticipants")
    List<Event> findFullEvents();

    /**
     * Check if event exists by name
     */
    boolean existsByEventName(String eventName);

    /**
     * Count events by status
     */
    long countByEventStatus(String eventStatus);
}
