package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dao.repository.OrderRepository;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DestinationDeliveryBean{

    private final CompanyInfo companyInfo;
    private final OrderRepository orderRepository;

    public DestinationDeliveryBean(CompanyInfo companyInfo, OrderRepository orderRepository) {
        this.companyInfo = companyInfo;
        this.orderRepository = orderRepository;
    }

    @SneakyThrows
    @Async("deliveryExecutor")
    public void deliverToDestination(Destination destination, List<Long> orderIdsToDeliver) {

        Integer distance = destination.getDistance();

        Iterable<Order> ordersToDeliver = orderRepository.findAllById(orderIdsToDeliver);
        ordersToDeliver.forEach(order -> order.changeOrderStatus(OrderStatus.DELIVERING));
        orderRepository.saveAll(ordersToDeliver);

        Thread.sleep(distance * 1000);

        int count = 0;
        ordersToDeliver = orderRepository.findAllById(orderIdsToDeliver);
        for (Order order : ordersToDeliver) {
            if (order.changeOrderStatus(OrderStatus.DELIVERED)) {
                count++;
            }
        }
        orderRepository.saveAll(ordersToDeliver);

        companyInfo.increaseProfit((long) count * distance);
    }
}
