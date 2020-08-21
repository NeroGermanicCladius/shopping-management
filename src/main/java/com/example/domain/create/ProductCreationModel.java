package com.example.domain.create;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreationModel {
    private String name;
    private BigDecimal price;
    private String overview;
    private BigDecimal rating;

    public ProductCreationModel() {
        this(null, null, null, null);
    }

    public ProductCreationModel(String name, BigDecimal price, String overview, BigDecimal rating) {
        this.name = name;
        this.price = price;
        this.overview = overview;
        this.rating = rating;
    }
}
