package event_management_ticket_booking_system.demo.repository;

import event_management_ticket_booking_system.demo.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VenueRepository extends JpaRepository<Venue, UUID> {
    Page<Venue> findByOwner_Id(UUID ownerId, Pageable pageable);
}
