package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private Role role;
}
