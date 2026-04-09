package event_management_ticket_booking_system.demo.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestDTO {
    private List<UUID> ticketIds;
}
