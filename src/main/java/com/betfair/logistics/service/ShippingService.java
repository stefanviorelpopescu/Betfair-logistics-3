package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.entity.OrderStatus;
import com.betfair.logistics.dao.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

@Service
public class ShippingService {

    private final CompanyInfo companyInfo;
    private final OrderRepository orderRepository;
    private final DestinationDeliveryBean destinationDeliveryBean;
    private final ExecutorService executorService;

    public ShippingService(CompanyInfo companyInfo,
                           OrderRepository orderRepository,
                           DestinationDeliveryBean destinationDeliveryBean,
                           @Qualifier("deliveryExecutor") ExecutorService executorService) {
        this.companyInfo = companyInfo;
        this.orderRepository = orderRepository;
        this.destinationDeliveryBean = destinationDeliveryBean;
//        this.executorService = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());
        this.executorService = executorService;
    }

    public void startNewDay() {

        Long currentDate = companyInfo.advanceCurrentDate();
        List<Order> ordersFromToday = orderRepository.findAllByDeliveryDate(currentDate);

//        Map<Destination, List<Order>> ordersByDestination = ordersFromToday.stream()
//                .filter(order -> order.getOrderStatus() == OrderStatus.NEW)
//                .collect(groupingBy(Order::getDestination));

        Map<Destination, List<Long>> orderIdsByDestination = ordersFromToday.stream()
                    .filter(order -> order.getOrderStatus() == OrderStatus.NEW)
                    .collect(groupingBy(Order::getDestination, mapping(Order::getId,Collectors.toList())));

//        orderIdsByDestination.forEach((key, value) -> executorService.submit(new DestinationDeliveryThread(key, value, companyInfo, orderRepository)));

        orderIdsByDestination.forEach(destinationDeliveryBean::deliverToDestination);
    }

}
