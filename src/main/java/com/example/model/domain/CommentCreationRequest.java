package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationRequest {
    private String commentText;
    private Long userId;
    private Long productId;

    public CommentCreationRequest() {
        this(null, null, null);
    }

    public CommentCreationRequest(
            final String commentText,
            final Long userId,
            final Long productId
    ) {
        this.commentText = commentText;
        this.userId = userId;
        this.productId = productId;
    }
}
