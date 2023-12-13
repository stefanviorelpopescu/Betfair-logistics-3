package com.betfair.logistics.dao.repository;

import com.betfair.logistics.dao.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByDeliveryDateAndDestination_nameContainingIgnoreCase(Long deliveryDate, String destination);

}
