package com.will.kiosk.model;

import java.time.LocalDateTime;

public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Category(int categoryId, String categoryName, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Category(String categoryName, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
