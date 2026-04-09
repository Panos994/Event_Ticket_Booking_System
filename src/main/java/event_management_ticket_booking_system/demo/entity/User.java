package event_management_ticket_booking_system.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Email
    @NotBlank
    @Column
    private String email;

    @NotBlank
    @Column
    private String password;

    @Column
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Order> bookings = new ArrayList<>();;

    @OneToMany(mappedBy="organizer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> eventsCreated = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
