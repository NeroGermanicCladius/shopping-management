package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreationRequest {
    private String name;
    private BigDecimal price;
    private String overview;

    public ProductCreationRequest() {
        this(null, null, null);
    }

    public ProductCreationRequest(String name, BigDecimal price, String overview) {
        this.name = name;
        this.price = price;
        this.overview = overview;
    }
}
