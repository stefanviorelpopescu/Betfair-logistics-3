package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dao.repository.DestinationRepository;
import com.betfair.logistics.dao.repository.OrderRepository;
import com.betfair.logistics.dto.OrderCreateDto;
import com.betfair.logistics.dto.converter.OrderConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betfair.logistics.dao.entity.OrderStatus.CANCELED;
import static com.betfair.logistics.dao.entity.OrderStatus.DELIVERED;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;

    public OrderService(OrderRepository orderRepository, DestinationRepository destinationRepository) {
        this.orderRepository = orderRepository;
        this.destinationRepository = destinationRepository;
    }

    public void cancelOrders(List<Long> orderIds) {
        Iterable<Order> foundOrders = orderRepository.findAllById(orderIds);
        foundOrders.forEach(order -> changeOrderStatus(order, CANCELED));
        orderRepository.saveAll(foundOrders);
    }

    private void changeOrderStatus(Order order, OrderStatus newStatus) {
        if (newStatus == CANCELED) {
            if (order.getOrderStatus() != DELIVERED) {
                order.setOrderStatus(CANCELED);
            }
        }
    }

    public void addOrders(List<OrderCreateDto> orderCreateDtos) {
        List<Order> ordersToSave = new ArrayList<>();
        for (OrderCreateDto orderCreateDto : orderCreateDtos) {
            if (isDtoValid(orderCreateDto)) {
                Optional<Destination> optional = destinationRepository.findById(orderCreateDto.getDestinationId());
                optional.ifPresent(destination -> {
                    Order order = OrderConverter.orderCreateDtoToEntity(orderCreateDto, destination);
                    ordersToSave.add(order);
                });
            }
        }
        orderRepository.saveAll(ordersToSave);
    }

    private boolean isDtoValid(OrderCreateDto orderCreateDto) {
        return true;
    }
}
