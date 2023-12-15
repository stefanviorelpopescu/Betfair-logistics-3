package com.betfair.logistics.service;

import com.betfair.logistics.dao.entity.Destination;
import com.betfair.logistics.dao.entity.Order;
import com.betfair.logistics.dao.repository.DestinationRepository;
import com.betfair.logistics.dao.repository.OrderRepository;
import com.betfair.logistics.dto.OrderCreateDto;
import com.betfair.logistics.dto.OrderDto;
import com.betfair.logistics.dto.converter.OrderConverter;
import com.betfair.logistics.exception.InvalidDateForOrderSearchException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betfair.logistics.dao.entity.OrderStatus.CANCELED;

@Service
public class OrderService {

    private final CompanyInfo companyInfo;
    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;

    public OrderService(CompanyInfo companyInfo, OrderRepository orderRepository, DestinationRepository destinationRepository) {
        this.companyInfo = companyInfo;
        this.orderRepository = orderRepository;
        this.destinationRepository = destinationRepository;
    }

    public void cancelOrders(List<Long> orderIds) {
        Iterable<Order> foundOrders = orderRepository.findAllById(orderIds);
        foundOrders.forEach(order -> order.changeOrderStatus(CANCELED));
        orderRepository.saveAll(foundOrders);
    }

    public List<OrderDto> addOrders(List<OrderCreateDto> orderCreateDtos) {
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
        return OrderConverter.orderListToOrderDtoList(ordersToSave);
    }

    private boolean isDtoValid(OrderCreateDto orderCreateDto) {
        if (orderCreateDto.getDestinationId() == null || orderCreateDto.getDeliveryDate() == null) {
            return false;
        }
        return orderCreateDto.getDeliveryDate() > companyInfo.getCurrentDate();
    }

    public List<OrderDto> getOrders(String dateParam, String destinationParam) throws InvalidDateForOrderSearchException {

        Long date = getDateFromDateParam(dateParam);
        List<Order> allByDeliveryDate = orderRepository.findAllByDeliveryDateAndDestination_nameContainingIgnoreCase(date, destinationParam);
        return OrderConverter.orderListToOrderDtoList(allByDeliveryDate);
    }

    private Long getDateFromDateParam(String dateParam) throws InvalidDateForOrderSearchException {

        if (dateParam != null && !dateParam.isBlank()) {
            return companyInfo.getDateAsLongFromString(dateParam);
        }
        return companyInfo.getCurrentDate();
    }
}
