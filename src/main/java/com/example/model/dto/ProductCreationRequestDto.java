package com.example.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreationRequestDto {
    private String name;
    private BigDecimal price;
    private String overview;

    public ProductCreationRequestDto() {
        this(null, null, null);
    }

    public ProductCreationRequestDto(String name, BigDecimal price, String overview) {
        this.name = name;
        this.price = price;
        this.overview = overview;
    }
}
