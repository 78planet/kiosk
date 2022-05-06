package com.will.kiosk.repository.product;

import com.will.kiosk.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product insert(Product category);

    List<Product> findAll();

    Product update(Product category);

    void delete(Product category);

    Optional<Product> findById(int categoryId);

    List<Product> findProductsByCategory(int categoryId);

}
