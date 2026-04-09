package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.*;
import event_management_ticket_booking_system.demo.entity.Order;
import event_management_ticket_booking_system.demo.service.OrderService;
import event_management_ticket_booking_system.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO dto, @RequestParam UUID userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dto, userId));
    }

    @GetMapping
    public ResponseEntity<List<CreateOrderResponseDTO>> getOrders(){
        return ResponseEntity.ok().body(orderService.getOrders().stream().map(this::mapToResponse).toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CreateOrderResponseDTO> getOrder(@PathVariable UUID orderId){
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok().body(mapToResponse(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PageResponseDTO<CreateOrderResponseDTO>> getUserOrders(@PathVariable UUID userId, Pageable pageable){
        Page<CreateOrderResponseDTO> page = orderService.findOrdersByUser(userId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateOrderResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok().body(res);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<CreateOrderResponseDTO> updateOrderStatus(@PathVariable UUID orderId,@RequestBody CreateOrderRequestDTO dto){
        CreateOrderResponseDTO response = orderService.cancelOrder(orderId, dto);
        return ResponseEntity.ok().body(response);
    }

    private CreateOrderResponseDTO mapToResponse(Order order){
        return CreateOrderResponseDTO.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .items(order.getOrderItems().stream().map(item -> CreateOrderItemResponseDTO.builder()
                        .ticketId(item.getTicket().getId())
                        .price(item.getOrder().getTotalPrice())
                        .seatNumber(item.getTicket().getSeatNumber())
                        .build()).toList()
        )
        .build();

    }
}
