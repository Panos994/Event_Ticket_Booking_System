package event_management_ticket_booking_system.demo.repository;

import event_management_ticket_booking_system.demo.entity.EventStatus;
import event_management_ticket_booking_system.demo.entity.Ticket;
import event_management_ticket_booking_system.demo.entity.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByEvent_IdAndStatus(UUID eventId, TicketStatus status);
    long countByEvent_IdAndStatus(UUID eventId, TicketStatus status);
    Page<Ticket> findByEvent_IdAndStatus(UUID eventId, TicketStatus status, Pageable pageable);

}
