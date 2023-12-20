package org.pk.lab4.controller;

import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/details")
public class ProductDetailsController {

    // TODO Remove?

    private final ProductService productService;

    public ProductDetailsController(ProductService productService) {
        this.productService = productService;
    }
}
