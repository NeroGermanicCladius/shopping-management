package com.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private Long id;
    private Integer rate;
    private Long userId;
    private Long productId;

    public RatingDto() {
        this(null, null, null, null);
    }

    @JsonCreator
    public RatingDto(
            @JsonProperty("id") final Long id,
            @JsonProperty("name") final Integer rate,
            @JsonProperty("price") final Long userId,
            @JsonProperty("overview") final Long productId
    ) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
        this.productId = productId;
    }
}
