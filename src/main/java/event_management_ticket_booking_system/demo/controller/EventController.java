package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.CreateEventRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateEventResponseDTO;
import event_management_ticket_booking_system.demo.dto.CreateVenueResponseDTO;
import event_management_ticket_booking_system.demo.dto.PageResponseDTO;
import event_management_ticket_booking_system.demo.entity.Event;
import event_management_ticket_booking_system.demo.service.EventService;
import event_management_ticket_booking_system.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;


    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(@RequestBody CreateEventRequestDTO dto, @RequestParam UUID organizerId){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(dto, organizerId));
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<PageResponseDTO<CreateEventResponseDTO>> getEventByOrganizer(@PathVariable UUID organizerId, Pageable pageable){
        Page<CreateEventResponseDTO> page = eventService.listByOrganizer(organizerId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateEventResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok().body(res);
    }


    @GetMapping("/venue/{venueId}")
    public ResponseEntity<PageResponseDTO<CreateEventResponseDTO>> getEventByVenue(@PathVariable UUID venueId, Pageable pageable){
        Page<CreateEventResponseDTO> page = eventService.listByVenue(venueId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateEventResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/{eventId}/submit")
    public ResponseEntity<CreateEventResponseDTO> submit(@PathVariable UUID eventId){
        return ResponseEntity.ok().body(mapToResponse(eventService.submitEvent(eventId)));
    }
    @PostMapping("/{id}/approve")
    public ResponseEntity<CreateEventResponseDTO> approve(@PathVariable UUID eventId){
        return ResponseEntity.ok().body(mapToResponse(eventService.approveEvent(eventId)));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<CreateEventResponseDTO> reject(@PathVariable UUID eventId){
        return ResponseEntity.ok().body(mapToResponse(eventService.rejectEvent(eventId)));
    }



    private CreateEventResponseDTO mapToResponse(Event event){
        return CreateEventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }

}
