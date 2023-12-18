package com.betfair.logistics.dto.converter;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dto.DestinationDto;

import java.util.List;
import java.util.stream.StreamSupport;

public class DestinationConverter {

    public static DestinationDto destinationToDestinationDto(Destination destination) {
        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .distance(destination.getDistance())
                .build();
    }

    public static List<DestinationDto> destinationListToDestionationDtoList(Iterable<Destination> destinations) {
        return StreamSupport.stream(destinations.spliterator(), false)
                .map(DestinationConverter::destinationToDestinationDto)
                .toList();
    }

    public static Destination destinationDtoToDestination(DestinationDto destinationDto) {
        Destination destination = new Destination();
        destination.setId(destinationDto.getId());
        destination.setName(destinationDto.getName());
        destination.setDistance(destinationDto.getDistance());
        return destination;
    }

}
