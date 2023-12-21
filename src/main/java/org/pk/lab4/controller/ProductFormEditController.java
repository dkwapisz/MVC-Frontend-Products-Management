package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
import org.pk.lab4.exception.NotFoundException;
import org.pk.lab4.exception.ServerErrorException;
import org.pk.lab4.exception.ValidationException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products/form/edit")
public class ProductFormEditController {

    private final ProductService productService;

    public ProductFormEditController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String getProductData(Model model, @PathVariable("id") String productId, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProductDetails(productId);
            model.addAttribute("product", product);
            return "product-form-edit";
        } catch (NotFoundException | ServerErrorException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/list";
        }
    }

    @PostMapping("/{id}")
    public String editProduct(Model model, @PathVariable("id") String productId, @ModelAttribute("product") Product product) {
        try {
            Product updatedProduct = productService.updateProduct(productId, product);
            model.addAttribute("product", updatedProduct);
            return "product-details";
        } catch (NotFoundException | ValidationException | ServerErrorException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("product", product);
            return "product-form-edit";
        }
    }
}
