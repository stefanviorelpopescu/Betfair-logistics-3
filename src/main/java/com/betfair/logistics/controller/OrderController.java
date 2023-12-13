package com.betfair.logistics.controller;

import com.betfair.logistics.dto.OrderCreateDto;
import com.betfair.logistics.dto.OrderDto;
import com.betfair.logistics.exception.InvalidDateForOrderSearchException;
import com.betfair.logistics.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public List<OrderDto> addOrders(@RequestBody List<OrderCreateDto> orderCreateDtos) {
        return orderService.addOrders(orderCreateDtos);
    }

    @PostMapping("/cancel")
    public void cancelOrders(@RequestBody List<Long> orderIds) {
        orderService.cancelOrders(orderIds);
    }

    @GetMapping("/status")
    public List<OrderDto> getOrders(@RequestParam(name = "date", required = false) String dateParam,
                          @RequestParam(name = "destination", required = false, defaultValue = "") String destinationParam)
            throws InvalidDateForOrderSearchException {
        return orderService.getOrders(dateParam, destinationParam);
    }

}
