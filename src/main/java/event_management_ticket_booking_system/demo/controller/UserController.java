package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.AssignRoleDTO;
import event_management_ticket_booking_system.demo.dto.CreateUserRegisterRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateUserRegisterResponseDTO;
import event_management_ticket_booking_system.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserRegisterResponseDTO> registerUser(@RequestBody CreateUserRegisterRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }
    @PostMapping("/assign")
    public ResponseEntity<Void> assignRoleToUser(@RequestBody AssignRoleDTO dto){
        userService.assignRole(dto.getUserId(), dto.getRole());
        return ResponseEntity.ok().build();
    }
}
