package event_management_ticket_booking_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private int capacity; //max chairs

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy="eventVenue", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();;
}
