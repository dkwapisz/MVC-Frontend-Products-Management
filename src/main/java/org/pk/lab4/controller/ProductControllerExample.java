package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductControllerExample {

    private final ProductService productService;

    public ProductControllerExample(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable String id, Model model) {
        Product product = productService.getProductDetails(id);
        model.addAttribute("product", product);
        return "productDetails";
    }

    @GetMapping("/edit/{id}")
    public String getEditProductForm(@PathVariable String id, Model model) {
        Product product = productService.getProductDetails(id);
        model.addAttribute("product", product);
        return "editProductForm";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product.getId(), product);
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String getNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "newProductForm";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute("product") Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
