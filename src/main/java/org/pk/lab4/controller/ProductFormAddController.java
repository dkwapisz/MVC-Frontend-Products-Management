package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
import org.pk.lab4.service.exception.ServerErrorException;
import org.pk.lab4.service.exception.ValidationException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/form/add")
public class ProductFormAddController {

    private final ProductService productService;

    public ProductFormAddController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public String addProduct(Model model, @ModelAttribute("product") Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            model.addAttribute("product", createdProduct);
            return "product-details";
        } catch (ValidationException | ServerErrorException e) {
            model.addAttribute("error", e.getMessage());
            return "product-form-add";
        }
    }
}
