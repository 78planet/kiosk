package com.will.kiosk.service.orders;

import com.will.kiosk.model.OrderStatus;
import com.will.kiosk.model.Orders;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Orders> getAllOrder();

    Orders createOrder(OrderStatus orderStatus, int orderTableNumber);

    Orders updateOrder(UUID orderId, OrderStatus orderStatus, int orderTableNumber);

    void deleteOrder(UUID orderId);
}
