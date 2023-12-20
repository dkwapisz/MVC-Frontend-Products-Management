package org.pk.lab4.service.http;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;

import java.util.List;

public interface HttpService {

    List<ProductSummary> getAllProducts();
    Product getProductDetails(String productId);

    Product createProduct(Product product);

    Product updateProduct(String productId, Product product);

    void deleteProduct(String productId);

}
