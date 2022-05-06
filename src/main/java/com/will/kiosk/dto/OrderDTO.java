package com.will.kiosk.dto;

import com.will.kiosk.model.OrderStatus;

import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        OrderStatus orderStatus,
        int orderTableNumber
) {
}
