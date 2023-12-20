package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/form/edit")
public class ProductFormEditController {

    private final ProductService productService;

    public ProductFormEditController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String getProductData(Model model, @PathVariable("id") String productId) {
        Product product = productService.getProductDetails(productId);
        model.addAttribute("product", product);
        return "product-form-edit";
    }

    @PostMapping("/{id}")
    public String editProduct(Model model, @PathVariable("id") String productId,
                              @ModelAttribute("product") Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        model.addAttribute("product", updatedProduct);
        return "product-details";
    }
}
