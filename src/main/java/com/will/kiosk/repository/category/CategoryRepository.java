package com.will.kiosk.repository.category;

import com.will.kiosk.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    Category insert(Category category);

    List<Category> findAll();

    Category update(Category category);

    void delete(Category category);

    Optional<Category> findById(int categoryId);

}
