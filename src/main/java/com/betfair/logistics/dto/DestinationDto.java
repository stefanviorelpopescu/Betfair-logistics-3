package com.betfair.logistics.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinationDto {

    @NotNull(message = "ID must be provided")
    Long id;

    @NotNull(message = "Name must be provided")
    String name;

    @NotNull(message = "Distance must be provided")
    @Min(message = "Distance must be positive", value = 1)
    Integer distance;
}
