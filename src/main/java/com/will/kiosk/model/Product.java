package com.will.kiosk.model;


import java.time.LocalDateTime;

public class Product {
    private int productId;
    private String productName;
    private String description;
    private int price;
    private final int categoryId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(int productId, String productName, String description, int price, int categoryId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product(String productName, String description, int price, int categoryId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
