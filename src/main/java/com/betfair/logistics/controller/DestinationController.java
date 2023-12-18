package com.betfair.logistics.controller;

import com.betfair.logistics.dto.DestinationCreateDto;
import com.betfair.logistics.dto.DestinationDto;
import com.betfair.logistics.exception.InvalidDestinationDtoException;
import com.betfair.logistics.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/all")
    public List<DestinationDto> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDto> getDestination(@PathVariable Long id) {
        Optional<DestinationDto> destinationById = destinationService.getDestinationById(id);
        return destinationById.map(destinationDto -> new ResponseEntity<>(destinationDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public DestinationDto addDestination(@RequestBody @Valid DestinationCreateDto destinationDto) throws InvalidDestinationDtoException {
        return destinationService.createDestination(destinationDto);
    }

    @PutMapping
    public DestinationDto editDestination(@RequestBody @Valid DestinationDto destinationDto) throws InvalidDestinationDtoException {
        return destinationService.editDestination(destinationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
    }

}
