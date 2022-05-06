package com.will.kiosk.service.product;

import com.will.kiosk.model.Product;
import com.will.kiosk.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findProductsByCategory(categoryId);
    }

    @Override
    public Product createProduct(String productName, String description, int price, int categoryId) {
        //TODO category Id 검증.
        Product product = new Product(productName, description, price, categoryId, LocalDateTime.now(), null);
        return productRepository.insert(product);
    }

    @Override
    public Product updateProduct(int productId, String productName, String description, int price, int categoryId) {
        //TODO category Id 검증.
        Product product = new Product(productId, productName, description, price, categoryId, null, LocalDateTime.now());
        return productRepository.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.delete(productId);
    }
}
