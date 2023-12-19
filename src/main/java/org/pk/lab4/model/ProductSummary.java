package org.pk.lab4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummary {

    private String id;
    private String name;
    private Integer quantity;
    private Float price;
    private Boolean available;

}
