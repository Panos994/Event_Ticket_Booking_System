package event_management_ticket_booking_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @ManyToOne
    private User organizer;

    @ManyToOne
    private Venue eventVenue;

    @OneToMany(mappedBy="event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();;


    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
