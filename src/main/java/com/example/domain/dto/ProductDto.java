package com.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String overview;
    private BigDecimal rating;

    public ProductDto() {
        this(null, null, null, null, null);
    }

    @JsonCreator
    public ProductDto(
            @JsonProperty("id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("overview") final String overview,
            @JsonProperty("rating") final BigDecimal rating
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.overview = overview;
        this.rating = rating;
    }
}
