package org.pk.lab4.controller;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.exception.NotFoundException;
import org.pk.lab4.exception.ServerErrorException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        try {
            List<ProductSummary> productSummaryList = productService.getAllProducts();
            model.addAttribute("productSummaries", productSummaryList);
            return "product-list";
        } catch (NotFoundException | ServerErrorException e) {
            model.addAttribute("error", e.getMessage());
            return "product-list";
        }
    }

    @GetMapping("/details/{id}")
    public String goToProductDetails(Model model, @PathVariable("id") String productId, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProductDetails(productId);
            model.addAttribute("product", product);
            return "product-details";
        } catch (NotFoundException | ServerErrorException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") String productId, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(productId);
            return "redirect:/products/list";
        } catch (NotFoundException | ServerErrorException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/list";
        }
    }

    @GetMapping("/create")
    public String goToAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form-add";
    }

    @GetMapping("/edit/{id}")
    public String goToEditForm(@PathVariable("id") String productId) {
        return "redirect:/products/form/edit/" + productId;
    }
}
