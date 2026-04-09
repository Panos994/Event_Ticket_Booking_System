package event_management_ticket_booking_system.demo.dto;
import event_management_ticket_booking_system.demo.entity.TicketStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketResponseDTO {

    private UUID id;
    private double price;
    private String seatNumber;
    private TicketStatus status;
    private UUID eventId;
}
