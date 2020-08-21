package com.example.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingCreationRequestDto {
    private Integer rate;
    private Long userId;
    private Long productId;

    public RatingCreationRequestDto() {
        this(null, null, null);
    }

    public RatingCreationRequestDto(
            final Integer rate,
            final Long userId,
            final Long productId
    ) {
        this.rate = rate;
        this.userId = userId;
        this.productId = productId;
    }
}
