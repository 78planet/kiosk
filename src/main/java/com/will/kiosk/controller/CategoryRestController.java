package com.will.kiosk.controller;

import com.will.kiosk.dto.CategoryDTO;
import com.will.kiosk.model.Category;
import com.will.kiosk.service.category.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public List<Category> productList() {
        return categoryService.getAllCategory();
    }

    @PostMapping("/category")
    public Category createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(
                categoryDTO.categoryName(),
                categoryDTO.description()
        );
    }

    @PutMapping("/category")
    public Category updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(
                categoryDTO.categoryId(),
                categoryDTO.categoryName(),
                categoryDTO.description()
        );
    }

    @DeleteMapping("/category/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
