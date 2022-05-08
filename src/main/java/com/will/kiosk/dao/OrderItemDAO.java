package com.will.kiosk.dao;

public record OrderItemDAO(
        int productId,
        String productName,
        int quantity,
        int price
) {

}
