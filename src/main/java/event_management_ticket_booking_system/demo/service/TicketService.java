package event_management_ticket_booking_system.demo.service;

import event_management_ticket_booking_system.demo.dto.CreateTicketRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateTicketResponseDTO;
import event_management_ticket_booking_system.demo.entity.Event;
import event_management_ticket_booking_system.demo.entity.EventStatus;
import event_management_ticket_booking_system.demo.entity.Ticket;
import event_management_ticket_booking_system.demo.entity.TicketStatus;
import event_management_ticket_booking_system.demo.repository.EventRepository;
import event_management_ticket_booking_system.demo.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TicketService(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO createTicketRequestDTO){
        Ticket ticket = new Ticket();
        Event event = eventRepository.findById(createTicketRequestDTO.getEventId()).orElseThrow(() -> new RuntimeException("Event not found with this id: " + eventId));
        ticket.setPrice(createTicketRequestDTO.getPrice());
        ticket.setSeatNumber(createTicketRequestDTO.getSeatNumber());
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setEvent(event);

        Ticket saved = ticketRepository.save(ticket);
        return CreateTicketResponseDTO.builder()
                .id(saved.getId())
                .price(saved.getPrice())
                .seatNumber(saved.getSeatNumber())
                .eventId(event.getId())
                .status(saved.getStatus())
                .build();
    }

    public Page<Ticket> findTicketsByEvent(UUID eventId, TicketStatus status, Pageable pageable){

        return ticketRepository.findByEvent_IdAndStatus(eventId, status, pageable);
    }

    public void markTicketAsSold(Ticket ticket){
        ticket.setStatus(TicketStatus.SOLD);
        ticketRepository.save(ticket);
    }

    public long countTicketsByStatus(UUID eventId, TicketStatus ticketStatus){
        return ticketRepository.countByEvent_IdAndStatus(eventId, ticketStatus);
    }

    public List<Ticket> findAvailableTickets(UUID eventId){

        List<Ticket> availableTickets = ticketRepository.findByEvent_IdAndStatus(eventId, TicketStatus.AVAILABLE);
        return availableTickets;

    }
}
