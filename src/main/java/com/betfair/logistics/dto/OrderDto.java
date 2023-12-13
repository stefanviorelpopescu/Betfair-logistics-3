package com.betfair.logistics.dto;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    Long id;
    Long deliveryDate;
    OrderStatus orderStatus;
    Long lastUpdated;
    Destination destination;
}
