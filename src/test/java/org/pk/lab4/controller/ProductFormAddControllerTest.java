package org.pk.lab4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pk.lab4.model.Product;
import org.pk.lab4.exception.ServerErrorException;
import org.pk.lab4.exception.ValidationException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductFormAddControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductFormAddController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductTest() throws ValidationException, ServerErrorException {
        // Given
        Product createdProduct = new Product();
        when(productService.createProduct(any())).thenReturn(createdProduct);

        // When
        String viewName = controller.addProduct(model, new Product());

        // Then
        assertEquals("product-details", viewName);
        verify(model).addAttribute("product", createdProduct);
    }

    @Test
    void addProductValidationErrorTest() throws ValidationException, ServerErrorException {
        // Given
        when(productService.createProduct(any())).thenThrow(new ValidationException());

        // When
        String viewName = controller.addProduct(model, new Product());

        // Then
        assertEquals("product-form-add", viewName);
        verify(model).addAttribute("error", "Validation failed - HTTP 400");
    }

    @Test
    void addProductServerErrorTest() throws ValidationException, ServerErrorException {
        // Given
        when(productService.createProduct(any())).thenThrow(new ServerErrorException());

        // When
        String viewName = controller.addProduct(model, new Product());

        // Then
        assertEquals("product-form-add", viewName);
        verify(model).addAttribute("error", "Server is not responding - HTTP 500");
    }
}
