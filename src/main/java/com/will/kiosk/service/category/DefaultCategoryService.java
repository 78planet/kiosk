package com.will.kiosk.service.category;

import com.will.kiosk.model.Category;
import com.will.kiosk.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(String categoryName, String description) {
        //TODO category name 검증.
        Category category = new Category(categoryName, description, LocalDateTime.now(), null);
        return categoryRepository.insert(category);
    }

    @Override
    public Category updateCategory(int categoryId, String categoryName, String description) {
        //TODO category Id 검증.
        Category category = new Category(categoryId, categoryName, description,null, LocalDateTime.now());
        return categoryRepository.update(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.delete(categoryId);
    }
}
