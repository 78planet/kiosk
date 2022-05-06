package com.will.kiosk.repository.product;

import com.will.kiosk.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product insert(Product product);

    List<Product> findAll();

    Product update(Product product);

    void delete(int productId);

    Optional<Product> findById(int productId);

    List<Product> findProductsByCategory(int productId);

}
