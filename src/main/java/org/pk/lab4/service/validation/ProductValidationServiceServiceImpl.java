package org.pk.lab4.service.validation;

import org.pk.lab4.model.Product;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ProductValidationServiceServiceImpl implements ProductValidationService {

    @Override
    public boolean isCreateValid(Product product) {
        if (isNull(product)) {
            return false;
        }

        if (isNull(product.getName()) || product.getName().isEmpty() ||
                isNull(product.getDescription()) || product.getDescription().isEmpty() ||
                isNull(product.getPrice()) || product.getPrice() <= 0 ||
                isNull(product.getQuantity()) || product.getQuantity() < 0 ||
                isNull(product.getWeight()) || product.getWeight() <= 0) {

            return false;
        }

        if (product.getName().length() < 3) {
            return false;
        }

        if (product.getDescription().length() < 3) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isUpdateValid(Product product) {
        boolean valid = false;

        if (nonNull(product)) {
            if (nonNull(product.getName())) {
                if (product.getName().length() >= 3) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(product.getDescription())) {
                if (product.getDescription().length() >= 3) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(product.getQuantity())) {
                if (product.getQuantity() >= 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(product.getPrice())) {
                if (product.getPrice() > 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(product.getWeight())) {
                if (product.getWeight() > 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(product.getProductCategory())) {
                valid = true;
            }
        }

        return valid;
    }
}
