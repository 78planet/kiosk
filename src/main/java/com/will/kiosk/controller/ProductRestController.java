package com.will.kiosk.controller;

import com.will.kiosk.dto.ProductDTO;
import com.will.kiosk.model.Product;
import com.will.kiosk.service.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> productList() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{categoryId}")
    public List<Product> productListByCategory(@PathVariable int categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(
                productDTO.productName(),
                productDTO.description(),
                productDTO.price(),
                productDTO.categoryId()
        );
    }

    @PutMapping("/product")
    public Product updateOrder(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(
                productDTO.productId(),
                productDTO.productName(),
                productDTO.description(),
                productDTO.price(),
                productDTO.categoryId()
        );
    }

    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }
}
