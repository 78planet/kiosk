package com.will.kiosk.dto;

import com.will.kiosk.model.OrderItem;
import com.will.kiosk.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        OrderStatus orderStatus,
        int orderTableNumber,
        List<OrderItem> orderItems
) {
}
