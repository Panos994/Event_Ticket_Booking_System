package event_management_ticket_booking_system.demo.service;

import event_management_ticket_booking_system.demo.dto.CreateOrderItemResponseDTO;
import event_management_ticket_booking_system.demo.dto.CreateOrderRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateOrderResponseDTO;
import event_management_ticket_booking_system.demo.entity.*;
import event_management_ticket_booking_system.demo.repository.OrderItemRepository;
import event_management_ticket_booking_system.demo.repository.OrderRepository;
import event_management_ticket_booking_system.demo.repository.TicketRepository;
import event_management_ticket_booking_system.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }
    @Transactional
    public CreateOrderResponseDTO createOrder(CreateOrderRequestDTO dto, UUID userId){
        Order order = new Order();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with this id: " + userId));
        List<Ticket> tickets = ticketRepository.findAllById(dto.getTicketIds());
        if(tickets.size() != dto.getTicketIds().size()){
            throw new IllegalArgumentException("One or more tickets not found with the provided ids!");
        }
        for(Ticket t : tickets){
            if(t.getStatus() != TicketStatus.AVAILABLE){
                throw new IllegalArgumentException("Ticket " + t.getId() + " is not available");
            }
        }
        double totalPrice = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalPrice(totalPrice);
        List<OrderItem> items = new ArrayList<>();
        for(Ticket tick : tickets){
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setTicket(tick);
            items.add(item);
            tick.setStatus(TicketStatus.SOLD);
            ticketRepository.save(tick);
        }
        orderItemRepository.saveAll(items);
        order.setOrderItems(items);
        orderRepository.save(order);
        return CreateOrderResponseDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(items.stream().map(i -> CreateOrderItemResponseDTO.builder()
                        .id(i.getId())
                        .ticketId(i.getTicket().getId())
                        .seatNumber(i.getTicket().getSeatNumber())
                        .price(i.getTicket().getPrice()).build()).toList())
        .build();


    }
    public Page<Order> findOrdersByUser(UUID userId, Pageable pageable){
        return orderRepository.findByUser_Id(userId, pageable);
    }

    public Order getOrder(UUID orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found with this id: " + orderId));
        return order;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public CreateOrderResponseDTO cancelOrder(UUID orderId, CreateOrderRequestDTO dto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found with this id: " + orderId));
        if(order.getOrderStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Only CREATED orders can be cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        for(OrderItem item : order.getOrderItems()){
            Ticket ticket = item.getTicket();
            ticket.setStatus(TicketStatus.AVAILABLE);

        }
        return CreateOrderResponseDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(order.getOrderItems().stream().map(i -> CreateOrderItemResponseDTO.builder()
                        .id(i.getId())
                        .ticketId(i.getTicket().getId())
                        .seatNumber(i.getTicket().getSeatNumber())
                        .price(i.getTicket().getPrice()).build()).toList())
                .build();
    }
}
