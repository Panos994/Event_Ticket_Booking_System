package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleDTO {
    private UUID userId;
    private Role role;
}