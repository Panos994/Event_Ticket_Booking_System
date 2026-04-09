package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.EventStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private EventStatus eventStatus;
    private UUID venueId;
}
