package com.will.kiosk.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Orders {
    private final UUID orderId;
    private OrderStatus orderStatus;
    private int orderTableNumber;
    private final List<OrderItem> orderItems;
    private final LocalDateTime createdAt;

    public Orders(UUID orderId, OrderStatus orderStatus, int orderTableNumber, List<OrderItem> orderItems, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderTableNumber = orderTableNumber;
        this.orderItems = orderItems;
        this.createdAt = createdAt;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
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

    public void setOrderTableNumber(int orderTableNumber) {
        this.orderTableNumber = orderTableNumber;
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
