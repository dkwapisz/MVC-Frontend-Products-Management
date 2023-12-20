package org.pk.lab4.service.model;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.service.http.HttpService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final HttpService httpService;

    public ProductServiceImpl(HttpService httpService) {
        this.httpService = httpService;
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
    public void createProduct(Product product) {
        httpService.createProduct(product);
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        return httpService.updateProduct(productId, product);
    }

    @Override
    public void deleteProduct(String productId) {
        httpService.deleteProduct(productId);
    }
}
