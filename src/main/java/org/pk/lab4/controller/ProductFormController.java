package org.pk.lab4.controller;

import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/form")
public class ProductFormController {

    private final ProductService productService;

    public ProductFormController(ProductService productService) {
        this.productService = productService;
    }
}
