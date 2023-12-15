package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dao.repository.OrderRepository;
import lombok.SneakyThrows;

import java.util.List;

public class DestinationDeliveryThread implements Runnable{

    private final Destination destination;
    private final List<Long> orderIdsToDeliver;
    private final CompanyInfo companyInfo;
    private final OrderRepository orderRepository;

    public DestinationDeliveryThread(Destination destination, List<Long> orderIdsToDeliver, CompanyInfo companyInfo, OrderRepository orderRepository) {
        this.destination = destination;
        this.orderIdsToDeliver = orderIdsToDeliver;
        this.companyInfo = companyInfo;
        this.orderRepository = orderRepository;
    }

    @SneakyThrows
    @Override
    public void run() {

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
