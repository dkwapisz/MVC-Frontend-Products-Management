package org.pk.lab4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.exception.NotFoundException;
import org.pk.lab4.exception.ServerErrorException;
import org.pk.lab4.service.model.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductListControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ProductListController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductListTest() throws NotFoundException, ServerErrorException {
        // Given
        List<ProductSummary> productSummaries = Collections.singletonList(new ProductSummary());
        when(productService.getAllProducts()).thenReturn(productSummaries);

        // When
        String viewName = controller.getProductList(model);

        // Then
        assertEquals("product-list", viewName);
        verify(model).addAttribute("productSummaries", productSummaries);
    }

    @Test
    void getProductListServerErrorTest() throws NotFoundException, ServerErrorException {
        // Given
        when(productService.getAllProducts()).thenThrow(new ServerErrorException());

        // When
        String viewName = controller.getProductList(model);

        // Then
        assertEquals("product-list", viewName);
        verify(model).addAttribute("error", "Server is not responding - HTTP 500");
    }

    @Test
    void goToProductDetailsTest() throws NotFoundException, ServerErrorException {
        // Given
        Product product = new Product();
        when(productService.getProductDetails(anyString())).thenReturn(product);

        // When
        String viewName = controller.goToProductDetails(model, "1", redirectAttributes);

        // Then
        assertEquals("product-details", viewName);
        verify(model).addAttribute("product", product);
    }

    @Test
    void goToProductDetailsNotFoundErrorTest() throws NotFoundException, ServerErrorException {
        // Given
        when(productService.getProductDetails(anyString())).thenThrow(new NotFoundException());

        // When
        String viewName = controller.goToProductDetails(model, "1", redirectAttributes);

        // Then
        assertEquals("redirect:/products/list", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Product not found - HTTP 404");
    }

    @Test
    void deleteProductByIdTest() throws NotFoundException, ServerErrorException {
        // Given
        String productId = "1";

        // When
        String viewName = controller.deleteProductById(productId, redirectAttributes);

        // Then
        assertEquals("redirect:/products/list", viewName);
        verify(productService).deleteProduct(productId);
    }

    @Test
    void deleteProductByIdNotFoundErrorTest() throws NotFoundException, ServerErrorException {
        // Given
        doThrow(new NotFoundException()).when(productService).deleteProduct(anyString());

        // When
        String viewName = controller.deleteProductById("1", redirectAttributes);

        // Then
        assertEquals("redirect:/products/list", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Product not found - HTTP 404");
    }

    @Test
    void goToAddFormTest() throws NotFoundException, ServerErrorException {
        // Given
        Product product = new Product();

        // When
        String viewName = controller.goToAddForm(model);

        // Then
        assertEquals("product-form-add", viewName);
        verify(model).addAttribute("product", product);
    }

    @Test
    void goToEditFormTest() throws NotFoundException, ServerErrorException {
        // Given
        String productId = "1";

        // When
        String viewName = controller.goToEditForm(productId);

        // Then
        assertEquals("redirect:/products/form/edit/1", viewName);
    }
}