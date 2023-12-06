package com.betfair.logistics.dao.repository;

import com.betfair.logistics.dao.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
