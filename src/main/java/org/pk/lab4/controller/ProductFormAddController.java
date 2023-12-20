package org.pk.lab4.controller;

import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/form/add")
public class ProductFormAddController {

    // TODO Impl

    private final ProductService productService;

    public ProductFormAddController(ProductService productService) {
        this.productService = productService;
    }
}
