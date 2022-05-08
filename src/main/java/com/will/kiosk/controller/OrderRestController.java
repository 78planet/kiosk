package com.will.kiosk.controller;

import com.will.kiosk.dao.OrderDAO;
import com.will.kiosk.dto.OrderDTO;
import com.will.kiosk.model.Orders;
import com.will.kiosk.service.orders.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Orders> orderList() {
        return orderService.getAllOrder();
    }

    @GetMapping("/orders/{orderId}")
    public OrderDAO orderList(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/order")
    public Orders createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(
                orderDTO.orderStatus(),
                orderDTO.orderTableNumber(),
                orderDTO.orderItems()
        );
    }

    @PutMapping("/order")
    public Orders updateOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(
                orderDTO.orderId(),
                orderDTO.orderStatus(),
                orderDTO.orderTableNumber()
        );
    }

    @DeleteMapping("/order/{orderId}")
    public void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
    }
}
