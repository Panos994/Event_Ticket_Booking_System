package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRegisterResponseDTO {
    private UUID id;
    private String username;
    private String fullName;
    private Role role;
}
