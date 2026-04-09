package event_management_ticket_booking_system.demo.repository;

import event_management_ticket_booking_system.demo.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByUser_Id(UUID userId, Pageable pageable);


}
