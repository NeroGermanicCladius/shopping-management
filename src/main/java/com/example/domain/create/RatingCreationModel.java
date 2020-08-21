package com.example.domain.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingCreationModel {
    private Integer rate;
    private Long userId;
    private Long productId;

    public RatingCreationModel() {
        this(null, null, null);
    }

    public RatingCreationModel(
            final Integer rate,
            final Long userId,
            final Long productId
    ) {
        this.rate = rate;
        this.userId = userId;
        this.productId = productId;
    }
}
