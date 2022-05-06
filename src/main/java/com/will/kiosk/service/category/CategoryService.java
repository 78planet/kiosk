package com.will.kiosk.service.category;

import com.will.kiosk.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    Category createCategory(String productName, String description);

    Category updateCategory(int categoryId, String productName, String description);

    void deleteCategory(int categoryId);
}
