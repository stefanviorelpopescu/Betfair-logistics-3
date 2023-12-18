package com.betfair.logistics.dao.cache;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.repository.DestinationRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class DestinationCache {

    private final DestinationRepository destinationRepository;

    private final Map<Long, Destination> destinationsById = new HashMap<>();
    private boolean isLoaded = false;


    public DestinationCache(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Collection<Destination> getAllDestinations() {
        if (!isLoaded) {
            reloadCache();
        }
        return destinationsById.values();
    }

    public void reloadCache() {
        StreamSupport.stream(destinationRepository.findAll().spliterator(), false)
                .forEach(destination -> destinationsById.put(destination.getId(), destination));
        isLoaded = true;
    }

    public Optional<Destination> getDestination(Long id) {
        if (!isLoaded) {
            reloadCache();
        }
        if (!destinationsById.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(destinationsById.get(id));
    }

    public void updateEntry(Destination destination) {
        destinationsById.put(destination.getId(), destination);
    }

    public void removeEntry(Long destinationId) {
        destinationsById.remove(destinationId);
    }

}
