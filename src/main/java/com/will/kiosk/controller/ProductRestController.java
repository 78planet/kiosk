package com.will.kiosk.controller;

import com.will.kiosk.dto.ProductDTO;
import com.will.kiosk.model.Product;
import com.will.kiosk.service.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/v1/products")
    public List<Product> productList() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/v1/products/{categoryId}")
    public List<Product> productListByCategory(@PathVariable int categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping("/api/v1/products")
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(
                productDTO.productName(),
                productDTO.description(),
                productDTO.price(),
                productDTO.categoryId()
        );
    }

    @PutMapping("/api/v1/products")
    public Product updateOrder(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(
                productDTO.productId(),
                productDTO.productName(),
                productDTO.description(),
                productDTO.price(),
                productDTO.categoryId()
        );
    }
}
