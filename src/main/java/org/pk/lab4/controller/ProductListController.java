package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
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
        model.addAttribute("productSummaries", productSummaryList);
        return "product-list";
    }

    @GetMapping("/details/{id}")
    public String goToProductDetails(Model model, @PathVariable("id") String productId) {
        Product product = productService.getProductDetails(productId);
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
        return "redirect:/products/list";
    }

    @GetMapping("/edit/{id}")
    public String goToEditForm(@PathVariable("id") String productId) {
        return "redirect:/products/form/edit/" + productId;
    }
}
