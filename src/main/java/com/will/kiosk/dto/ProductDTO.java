package com.will.kiosk.dto;

public record ProductDTO(
        int productId,
    String productName,
    String description,
    int price,
    int categoryId
) {
}
