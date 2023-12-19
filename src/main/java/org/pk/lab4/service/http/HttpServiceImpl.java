package org.pk.lab4.service.http;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HttpServiceImpl implements HttpService {

    @Override
    public List<ProductSummary> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductDetails(String productId) {
        return null;
    }

    @Override
    public void createProduct(Product product) {

    }

    @Override
    public void updateProduct(String productId, Product product) {

    }

    @Override
    public void deleteProduct(String productId) {

    }
}
