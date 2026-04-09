package event_management_ticket_booking_system.demo.dto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketRequestDTO {
    private double price;
    private String seatNumber;
    private UUID eventId;

}
