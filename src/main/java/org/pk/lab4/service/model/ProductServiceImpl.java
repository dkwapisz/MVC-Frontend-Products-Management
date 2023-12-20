package org.pk.lab4.service.model;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.service.exception.ValidationException;
import org.pk.lab4.service.http.HttpService;
import org.pk.lab4.service.validation.ProductValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final HttpService httpService;
    private final ProductValidation productValidation;

    public ProductServiceImpl(HttpService httpService, ProductValidation productValidation) {
        this.httpService = httpService;
        this.productValidation = productValidation;
    }

    @Override
    public List<ProductSummary> getAllProducts() {
        return httpService.getAllProducts();
    }

    @Override
    public Product getProductDetails(String productId) {
        return httpService.getProductDetails(productId);
    }

    @Override
    public Product createProduct(Product product) {
        if (productValidation.isCreateValid(product)) {
            return httpService.createProduct(product);
        } else {
            throw new ValidationException();
        }
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        if (productValidation.isUpdateValid(product)) {
            return httpService.updateProduct(productId, product);
        } else {
            throw new ValidationException();
        }
    }

    @Override
    public void deleteProduct(String productId) {
        httpService.deleteProduct(productId);
    }
}
