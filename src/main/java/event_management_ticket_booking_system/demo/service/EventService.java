package event_management_ticket_booking_system.demo.service;

import event_management_ticket_booking_system.demo.dto.CreateEventRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateEventResponseDTO;
import event_management_ticket_booking_system.demo.entity.Event;
import event_management_ticket_booking_system.demo.entity.EventStatus;
import event_management_ticket_booking_system.demo.entity.User;
import event_management_ticket_booking_system.demo.entity.Venue;
import event_management_ticket_booking_system.demo.repository.EventRepository;
import event_management_ticket_booking_system.demo.repository.UserRepository;
import event_management_ticket_booking_system.demo.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    public CreateEventResponseDTO createEvent(CreateEventRequestDTO dto, UUID organizerId){
       Event event = new Event();
       Venue venue = venueRepository.findById(dto.getVenueId()).orElseThrow(() -> new IllegalArgumentException("Venue not found with this id: " + dto.getVenueId()));
       User organizer = userRepository.findById(organizerId).orElseThrow(() -> new IllegalArgumentException("User not found with this id: " + organizerId));

       event.setDescription(dto.getDescription());
       event.setTitle(dto.getTitle());
       event.setStartDate(dto.getStartDate());
       event.setEndDate(dto.getEndDate());
       event.setOrganizer(organizer);
       event.setEventStatus(EventStatus.DRAFT);
       event.setEventVenue(venue);
       eventRepository.save(event);
       return CreateEventResponseDTO.builder()
               .id(event.getId())
               .title(event.getTitle())
               .description(event.getDescription())
               .startDate(event.getStartDate())
               .endDate(event.getEndDate())
               .eventStatus(event.getEventStatus())
               .venueId(venue.getId())
               .build();
    }

    public Page<Event> listByOrganizer(UUID organizerId, Pageable pageable){
        return eventRepository.findByOrganizer_Id(organizerId, pageable);
    }

    public Page<Event> listByVenue(UUID venueId, Pageable pageable){
        return eventRepository.findByVenue_Id(venueId, pageable);
    }

    public Event submitEvent(UUID eventId){
        return eventRepository.findById(eventId).map(event -> {
            event.setEventStatus(EventStatus.SUBMITTED);
            return eventRepository.save(event);
        }).orElseThrow(() -> new IllegalArgumentException("Event not found with this id: " + eventId));
    }

    public Event approveEvent(UUID eventId){
        return eventRepository.findById(eventId).map(event -> {
            event.setEventStatus(EventStatus.APPROVED);
            return eventRepository.save(event);
        }).orElseThrow(() -> new IllegalArgumentException("Event not found with this id: " + eventId));
    }

    public Event rejectEvent(UUID eventId){
        return eventRepository.findById(eventId).map(event -> {
            event.setEventStatus(EventStatus.REJECTED);
            return eventRepository.save(event);
        }).orElseThrow(() -> new IllegalArgumentException("Event not found with this id: " + eventId));
    }
}
