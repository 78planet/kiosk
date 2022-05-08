package com.will.kiosk.dao;

import com.will.kiosk.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderDAO(
        UUID orderId,
        OrderStatus orderStatus,
        int orderTableNumber,
        List<OrderItemDAO> orderItems
) {
}
