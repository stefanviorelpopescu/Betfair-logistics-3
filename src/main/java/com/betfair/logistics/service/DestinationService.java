package com.betfair.logistics.service;

import com.betfair.logistics.dao.cache.DestinationCache;
import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.repository.DestinationRepository;
import com.betfair.logistics.dto.DestinationCreateDto;
import com.betfair.logistics.dto.DestinationDto;
import com.betfair.logistics.dto.converter.DestinationConverter;
import com.betfair.logistics.exception.InvalidDestinationDtoException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationCache destinationCache;

    public DestinationService(DestinationRepository destinationRepository, DestinationCache destinationCache) {
        this.destinationRepository = destinationRepository;
        this.destinationCache = destinationCache;
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
        destinationCache.removeEntry(id);
    }

    public Optional<DestinationDto> getDestinationById(Long id) {
        Optional<Destination> optionalDestination = destinationCache.getDestination(id);
        return optionalDestination.map(DestinationConverter::destinationToDestinationDto);
    }

    public DestinationDto createDestination(@Valid DestinationCreateDto destinationDto) throws InvalidDestinationDtoException {

        Destination destination = new Destination();
        destination.setName(destinationDto.getName());
        destination.setDistance(destinationDto.getDistance());

        try {
            destinationRepository.save(destination);
            destinationCache.updateEntry(destination);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDestinationDtoException(String.format("Provided name:[%s] already exists", destinationDto.getName()));
        }

        return DestinationConverter.destinationToDestinationDto(destination);
    }

    public DestinationDto editDestination(DestinationDto destinationDto) throws InvalidDestinationDtoException {

        Destination foundDestination = destinationCache.getDestination(destinationDto.getId())
                .orElseThrow(() -> new InvalidDestinationDtoException("Provided ID does not exist"));

        foundDestination.setName(destinationDto.getName());
        foundDestination.setDistance(destinationDto.getDistance());

        try {
            destinationRepository.save(foundDestination);
            destinationCache.updateEntry(foundDestination);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDestinationDtoException(String.format("Provided name:[%s] already exists", destinationDto.getName()));
        }

        return DestinationConverter.destinationToDestinationDto(foundDestination);
    }

    public List<DestinationDto> getAllDestinations() {
        return DestinationConverter.destinationListToDestionationDtoList(destinationCache.getAllDestinations());
    }
}
