package com.betfair.logistics.dto.converter;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dto.OrderCreateDto;

public class OrderConverter {

    public static Order orderCreateDtoToEntity(OrderCreateDto orderCreateDto, Destination destination) {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)
                .lastUpdated(System.currentTimeMillis())
                .destination(destination)
                .deliveryDate(orderCreateDto.getDeliveryDate())
                .build();
    }

}
