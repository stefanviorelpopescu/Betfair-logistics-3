package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dao.repository.OrderRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DestinationDeliveryBean{

    private final Logger logger = LoggerFactory.getLogger(DestinationDeliveryBean.class);
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

        logger.info(String.format("Shipping %d packages for %d km to %s", orderIdsToDeliver.size(), destination.getDistance(), destination.getName()));

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
