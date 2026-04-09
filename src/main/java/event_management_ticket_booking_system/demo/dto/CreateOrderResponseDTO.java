package event_management_ticket_booking_system.demo.dto;

import event_management_ticket_booking_system.demo.entity.EventStatus;
import event_management_ticket_booking_system.demo.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderResponseDTO {
    private UUID id;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<CreateOrderItemResponseDTO> items;


}
