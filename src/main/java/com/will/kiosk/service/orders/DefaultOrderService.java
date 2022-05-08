package com.will.kiosk.service.orders;

import com.will.kiosk.dao.OrderDAO;
import com.will.kiosk.model.OrderItem;
import com.will.kiosk.model.OrderStatus;
import com.will.kiosk.model.Orders;
import com.will.kiosk.repository.orders.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Orders createOrder(OrderStatus orderStatus, int orderTableNumber, List<OrderItem> orderItems) {
        //TODO order name 검증.
        Orders order = new Orders(UUID.randomUUID(), orderStatus, orderTableNumber, orderItems, LocalDateTime.now());
        return orderRepository.insert(order);
    }

    @Override
    public Orders updateOrder(UUID orderId, OrderStatus orderStatus, int orderTableNumber) {
        //TODO order Id 검증.
        Orders order = new Orders(orderId, orderStatus, orderTableNumber, null, null);
        return orderRepository.update(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.delete(orderId);
    }

    @Override
    public OrderDAO getOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }
}
