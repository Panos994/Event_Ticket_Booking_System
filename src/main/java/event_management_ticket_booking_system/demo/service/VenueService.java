package event_management_ticket_booking_system.demo.service;

import event_management_ticket_booking_system.demo.dto.CreateVenueRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateVenueResponseDTO;
import event_management_ticket_booking_system.demo.entity.User;
import event_management_ticket_booking_system.demo.entity.Venue;
import event_management_ticket_booking_system.demo.repository.UserRepository;
import event_management_ticket_booking_system.demo.repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    public VenueService(VenueRepository venueRepository, UserRepository userRepository) {
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    public CreateVenueResponseDTO createVenue(CreateVenueRequestDTO dto, UUID ownerId){
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("User not found with this id: " + ownerId));
        Venue venue = new Venue();
        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        venue.setCapacity(dto.getCapacity());
        venue.setOwner(owner);

        venueRepository.save(venue);
        return CreateVenueResponseDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .capacity(venue.getCapacity())
                .ownerId(ownerId)
                .build();
    }

    public Page<Venue> listVenueByOwner(UUID ownerId, Pageable pageable){
        return venueRepository.findByOwner_Id(ownerId, pageable);
    }

    public List<Venue> getAllVenues(){
        return venueRepository.findAll();
    }

    public Venue findVenueById(UUID venueId){
        return venueRepository.findById(venueId).orElseThrow(() -> new EntityNotFoundException("Venue not found with this id: " + venueId));
    }

}
