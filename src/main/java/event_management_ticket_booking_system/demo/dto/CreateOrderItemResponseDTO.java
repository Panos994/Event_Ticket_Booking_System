package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.Order;
import event_management_ticket_booking_system.demo.entity.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderItemResponseDTO {
    private UUID id;
    private UUID ticketId;
    private double price;
    private String seatNumber;

}
