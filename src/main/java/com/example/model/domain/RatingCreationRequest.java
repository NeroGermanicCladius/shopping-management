package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingCreationRequest {
    private Integer rate;
    private Long userId;
    private Long productId;

    public RatingCreationRequest() {
        this(null, null, null);
    }

    public RatingCreationRequest(
            final Integer rate,
            final Long userId,
            final Long productId
    ) {
        this.rate = rate;
        this.userId = userId;
        this.productId = productId;
    }
}
