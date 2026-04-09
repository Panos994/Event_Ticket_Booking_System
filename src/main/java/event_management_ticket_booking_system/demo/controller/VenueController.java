package event_management_ticket_booking_system.demo.controller;

import event_management_ticket_booking_system.demo.dto.CreateVenueRequestDTO;
import event_management_ticket_booking_system.demo.dto.CreateVenueResponseDTO;
import event_management_ticket_booking_system.demo.dto.PageResponseDTO;
import event_management_ticket_booking_system.demo.entity.Venue;
import event_management_ticket_booking_system.demo.service.VenueService;
import event_management_ticket_booking_system.demo.utils.PageMapper;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<CreateVenueResponseDTO> create(@RequestBody CreateVenueRequestDTO dto, @RequestParam UUID ownerId){
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.createVenue(dto, ownerId));
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponseDTO<CreateVenueResponseDTO>> getVenueByOwner(@RequestParam UUID ownerId, Pageable pageable){
        Page<CreateVenueResponseDTO> page = venueService.listVenueByOwner(ownerId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateVenueResponseDTO> response = PageMapper.toResponse(page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateVenueResponseDTO>> getAllVenues(){
        List<CreateVenueResponseDTO> venues = venueService.getAllVenues().stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<CreateVenueResponseDTO> getVenueById(@PathVariable UUID venueId){
        return ResponseEntity.status(HttpStatus.OK).body(mapToResponse(venueService.findVenueById(venueId)));
    }
    private CreateVenueResponseDTO mapToResponse(Venue venue){
        return CreateVenueResponseDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .capacity(venue.getCapacity())
                .ownerId(venue.getOwner().getId())
                .build();
    }
}
