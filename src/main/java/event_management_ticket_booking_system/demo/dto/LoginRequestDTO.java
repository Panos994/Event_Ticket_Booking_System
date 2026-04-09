package event_management_ticket_booking_system.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
