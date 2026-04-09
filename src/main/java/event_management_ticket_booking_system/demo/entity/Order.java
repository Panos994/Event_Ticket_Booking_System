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
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private double totalPrice;

    @Column
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private LocalDateTime createdAt;



    @ManyToOne
    private User user;

    @OneToMany(mappedBy="order", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
