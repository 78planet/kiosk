package com.will.kiosk.service.product;

import com.will.kiosk.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int categoryId);

    Product createProduct(String productName, String description, int price, int categoryId);

    Product updateProduct(int productId, String productName, String description, int price, int categoryId);

    void deleteProduct(int productId);

}
