package event_management_ticket_booking_system.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVenueResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private int capacity; //max chairs
    private UUID ownerId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
