package org.pk.lab4.model.utils;

import org.pk.lab4.model.ProductCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryConverter implements Converter<String, ProductCategory> {

    @Override
    public ProductCategory convert(String source) {
        try {
            return ProductCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product category: " + source);
        }
    }
}