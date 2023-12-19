package org.pk.lab4.controller;

import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products/list")
public class ProductListController {

    private final ProductService productService;

    public ProductListController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProductList(Model model) {
        List<ProductSummary> productSummaryList = productService.getAllProducts();
        model.addAttribute("products", productSummaryList);
        return "product-list";
    }

    @GetMapping("/form")
    public String redirectToProductForm(Model model) {
        // TODO
        return "product-form";
    }

    @PatchMapping("/{id}")
    public String editProduct(@PathVariable("id") String productId, Model model) {
        return "product-form";
    }
}
