package event_management_ticket_booking_system.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRegisterRequestDTO {
    private String email;
    private String password;
    private String fullName;
}
