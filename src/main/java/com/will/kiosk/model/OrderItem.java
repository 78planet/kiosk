package com.will.kiosk.model;

import java.util.UUID;

public record OrderItem(
        UUID orderId,
        int productId,
        int quantity
) {
}
