package com.betfair.logistics.controller;

import com.betfair.logistics.dto.OrderCreateDto;
import com.betfair.logistics.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public void addOrders(@RequestBody List<OrderCreateDto> orderCreateDtos) {
        orderService.addOrders(orderCreateDtos);
    }

    @PostMapping("/cancel")
    public void cancelOrders(@RequestBody List<Long> orderIds) {
        orderService.cancelOrders(orderIds);
    }

}
