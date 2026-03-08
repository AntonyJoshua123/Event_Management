package com.eventmanagement.service;

import com.eventmanagement.dto.EventDTO;
import com.eventmanagement.entity.Event;
import com.eventmanagement.exception.InvalidInputException;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Event Service Implementation
 * Contains business logic for Event operations
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        // Validate event date
        if (eventDTO.getEventDate().isBefore(LocalDate.now())) {
            throw new InvalidInputException("Event date must be in the future");
        }

        // Convert DTO to Entity
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setEventDescription(eventDTO.getEventDescription());
        event.setEventDate(eventDTO.getEventDate());
        event.setEventLocation(eventDTO.getEventLocation());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setRegisteredCount(0);
        event.setEventStatus("UPCOMING");

        // Save to database
        Event savedEvent = eventRepository.save(event);

        // Convert Entity to DTO and return
        return convertToDTO(savedEvent);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
        return convertToDTO(event);
    }

    @Override
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));

        // Update fields
        if (eventDTO.getEventName() != null && !eventDTO.getEventName().isEmpty()) {
            event.setEventName(eventDTO.getEventName());
        }
        if (eventDTO.getEventDescription() != null) {
            event.setEventDescription(eventDTO.getEventDescription());
        }
        if (eventDTO.getEventDate() != null) {
            event.setEventDate(eventDTO.getEventDate());
        }
        if (eventDTO.getEventLocation() != null && !eventDTO.getEventLocation().isEmpty()) {
            event.setEventLocation(eventDTO.getEventLocation());
        }
        if (eventDTO.getMaxParticipants() != null) {
            if (eventDTO.getMaxParticipants() < event.getRegisteredCount()) {
                throw new InvalidInputException(
                    "Max participants cannot be less than registered count (" + 
                    event.getRegisteredCount() + ")"
                );
            }
            event.setMaxParticipants(eventDTO.getMaxParticipants());
        }
        if (eventDTO.getEventStatus() != null && !eventDTO.getEventStatus().isEmpty()) {
            event.setEventStatus(eventDTO.getEventStatus().toUpperCase());
        }

        Event updatedEvent = eventRepository.save(event);
        return convertToDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
        eventRepository.delete(event);
    }

    @Override
    public List<EventDTO> searchEventsByName(String eventName) {
        List<Event> events = eventRepository.findByEventNameContainingIgnoreCase(eventName);
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> filterEventsByStatus(String status) {
        List<Event> events = eventRepository.findByEventStatusOrderByEventDateAsc(status.toUpperCase());
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO registerParticipant(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));

        // Check if event is full
        if (event.getRegisteredCount() >= event.getMaxParticipants()) {
            throw new InvalidInputException(
                "Event is full. Cannot register more participants. " +
                "Current: " + event.getRegisteredCount() + "/" + event.getMaxParticipants()
            );
        }

        // Check if event is cancelled
        if ("CANCELLED".equals(event.getEventStatus())) {
            throw new InvalidInputException("Cannot register for a cancelled event");
        }

        // Increment registered count
        event.setRegisteredCount(event.getRegisteredCount() + 1);
        Event updatedEvent = eventRepository.save(event);

        return convertToDTO(updatedEvent);
    }

    @Override
    public List<EventDTO> getEventsWithAvailableSeats() {
        List<Event> events = eventRepository.findEventsWithAvailableSeats();
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getEventStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalEvents = eventRepository.count();
        long upcomingEvents = eventRepository.countByEventStatus("UPCOMING");
        long completedEvents = eventRepository.countByEventStatus("COMPLETED");
        long cancelledEvents = eventRepository.countByEventStatus("CANCELLED");
        
        List<Event> allEvents = eventRepository.findAll();
        int totalParticipants = allEvents.stream()
                .mapToInt(Event::getRegisteredCount)
                .sum();
        
        stats.put("totalEvents", totalEvents);
        stats.put("upcomingEvents", upcomingEvents);
        stats.put("completedEvents", completedEvents);
        stats.put("cancelledEvents", cancelledEvents);
        stats.put("totalParticipants", totalParticipants);
        
        return stats;
    }

    /**
     * Helper method to convert Entity to DTO
     */
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setEventId(event.getEventId());
        dto.setEventName(event.getEventName());
        dto.setEventDescription(event.getEventDescription());
        dto.setEventDate(event.getEventDate());
        dto.setEventLocation(event.getEventLocation());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setRegisteredCount(event.getRegisteredCount());
        dto.setEventStatus(event.getEventStatus());
        dto.setCreatedDatetime(event.getCreatedDatetime());
        dto.setUpdatedDatetime(event.getUpdatedDatetime());
        return dto;
    }
}
