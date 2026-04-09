package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.CreateTicketRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateTicketResponseDTO;
import event_management_ticket_booking_system.demo.dto.PageResponseDTO;
import event_management_ticket_booking_system.demo.entity.Ticket;
import event_management_ticket_booking_system.demo.entity.TicketStatus;
import event_management_ticket_booking_system.demo.service.TicketService;
import event_management_ticket_booking_system.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<CreateTicketResponseDTO> createTicket(@RequestBody CreateTicketRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(dto));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<PageResponseDTO<CreateTicketResponseDTO>> getTicketByEvent(@PathVariable UUID eventId, @RequestParam TicketStatus status, Pageable pageable){
        Page<CreateTicketResponseDTO> page = ticketService.findTicketsByEvent(eventId, status, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateTicketResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("event/{eventId}/available")
    public ResponseEntity<List<CreateTicketResponseDTO>> getAvailableTickets(@PathVariable UUID eventId){
        return ResponseEntity.ok().body(ticketService.findAvailableTickets(eventId).stream().map(this::mapToResponse).toList());
    }

    private CreateTicketResponseDTO mapToResponse(Ticket ticket){
        return CreateTicketResponseDTO.builder()
                .id(ticket.getId())
                .eventId(ticket.getEvent().getId())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .build();
    }
}
