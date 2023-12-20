package org.pk.lab4.service.validation;

import org.pk.lab4.model.Product;

public interface ProductValidation {

    boolean isCreateValid(Product product);

    boolean isUpdateValid(Product product);
}
