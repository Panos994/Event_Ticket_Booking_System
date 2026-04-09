package event_management_ticket_booking_system.demo.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequestDTO {

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID venueId;
}
