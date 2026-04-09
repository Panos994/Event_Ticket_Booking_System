package event_management_ticket_booking_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private double price;

    @Column
    private String seatNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    private Event event;
}
