package com.will.kiosk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("customer")
    public String newCustomerPage() {
        return "customer-page";
    }

    @GetMapping("admin")
    public String newAdminPage() {
        return "admin-page";
    }

    @GetMapping("category")
    public String newCategoryPage() {
        return "category-page";
    }

    @GetMapping("product")
    public String newProductPage() {
        return "product-page";
    }
}
