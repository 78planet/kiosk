package com.will.kiosk.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Orders {
    private final UUID orderId;
    private OrderStatus orderStatus;
    private final int orderTableNumber;
    private final LocalDateTime createdAt;

    public Orders(UUID orderId, OrderStatus orderStatus, int orderTableNumber, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderTableNumber = orderTableNumber;
        this.createdAt = createdAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getOrderTableNumber() {
        return orderTableNumber;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                ", createdAt=" + createdAt +
                ", orderTableNumber=" + orderTableNumber +
                '}';
    }
}
