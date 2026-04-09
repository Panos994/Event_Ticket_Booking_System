package event_management_ticket_booking_system.demo.repository;

import event_management_ticket_booking_system.demo.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByOrganizer_Id(UUID organizerId, Pageable pageable);
    Page<Event> findByVenue_Id(UUID venueId, Pageable pageable);
}
