package com.betfair.logistics.dto.converter;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dto.OrderCreateDto;
import com.betfair.logistics.dto.OrderDto;

import java.util.List;

public class OrderConverter {

    public static Order orderCreateDtoToEntity(OrderCreateDto orderCreateDto, Destination destination) {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)
                .lastUpdated(System.currentTimeMillis())
                .destination(destination)
                .deliveryDate(orderCreateDto.getDeliveryDate())
                .build();
    }

    public static OrderDto orderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .destination(order.getDestination())
                .deliveryDate(order.getDeliveryDate())
                .lastUpdated(order.getLastUpdated())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static List<OrderDto> orderListToOrderDtoList(List<Order> orderList) {
        return orderList.stream()
                .map(OrderConverter::orderToOrderDto)
                .toList();
    }

}
