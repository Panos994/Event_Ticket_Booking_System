package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.LoginRequestDTO;
import event_management_ticket_booking_system.demo.dto.LoginResponseDTO;
import event_management_ticket_booking_system.demo.security.CustomUserDetails;
import event_management_ticket_booking_system.demo.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(customUserDetails.getUser().getId(), customUserDetails.getUsername());
        return ResponseEntity.ok(LoginResponseDTO.builder().token(token).role(customUserDetails.getUser().getRole()).build());
    }
}
