package event_management_ticket_booking_system.demo.service;

import event_management_ticket_booking_system.demo.dto.CreateUserRegisterRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateUserRegisterResponseDTO;
import event_management_ticket_booking_system.demo.entity.Role;
import event_management_ticket_booking_system.demo.entity.User;
import event_management_ticket_booking_system.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateUserRegisterResponseDTO register(CreateUserRegisterRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("User already exists with email: " + dto.getEmail());
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        return CreateUserRegisterResponseDTO.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .username(savedUser.getEmail())
                .build();
    }
    @Transactional
    public void assignRole(UUID userId, Role newRole){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.setRole(newRole);
        userRepository.save(user);

    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
