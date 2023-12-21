package org.pk.lab4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pk.lab4.model.Product;
import org.pk.lab4.exception.NotFoundException;
import org.pk.lab4.exception.ServerErrorException;
import org.pk.lab4.exception.ValidationException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductFormEditControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ProductFormEditController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductDataTest() throws NotFoundException, ServerErrorException {
        // Given
        Product product = new Product();
        when(productService.getProductDetails(anyString())).thenReturn(product);

        // When
        String viewName = controller.getProductData(model, "1", redirectAttributes);

        // Then
        assertEquals("product-form-edit", viewName);
        verify(model).addAttribute("product", product);
    }

    @Test
    void getProductDataNotFoundErrorTest() throws NotFoundException, ServerErrorException {
        // Given
        when(productService.getProductDetails(anyString())).thenThrow(new NotFoundException());

        // When
        String viewName = controller.getProductData(model, "1", redirectAttributes);

        // Then
        assertEquals("redirect:/products/list", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Product not found - HTTP 404");
    }

    @Test
    void editProductTest() throws NotFoundException, ValidationException, ServerErrorException {
        // Given
        Product updatedProduct = new Product();
        when(productService.updateProduct(anyString(), any())).thenReturn(updatedProduct);

        // When
        String viewName = controller.editProduct(model, "1", new Product());

        // Then
        assertEquals("product-details", viewName);
        verify(model).addAttribute("product", updatedProduct);
    }

    @Test
    void editProductValidationErrorTest() throws NotFoundException, ValidationException, ServerErrorException {
        // Given
        when(productService.updateProduct(anyString(), any())).thenThrow(new ValidationException());

        // When
        String viewName = controller.editProduct(model, "1", new Product());

        // Then
        assertEquals("product-form-edit", viewName);
        verify(model).addAttribute("error", "Validation failed - HTTP 400");
    }
}