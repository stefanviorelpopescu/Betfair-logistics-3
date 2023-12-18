package com.betfair.logistics.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinationCreateDto {

    @Null(message = "ID must be null")
    Long id;

    @NotNull(message = "Name must be provided")
    String name;

    @NotNull(message = "Distance must be provided")
    @Min(message = "Distance must be positive", value = 1)
    Integer distance;
}
