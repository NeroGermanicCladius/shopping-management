package com.example.domain.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationModel {
    private String commentText;
    private Long userId;
    private Long productId;

    public CommentCreationModel() {
        this(null, null, null);
    }

    public CommentCreationModel(
            final String commentText,
            final Long userId,
            final Long productId
    ) {
        this.commentText = commentText;
        this.userId = userId;
        this.productId = productId;
    }
}
