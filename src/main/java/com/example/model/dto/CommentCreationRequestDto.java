package com.example.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationRequestDto {
    private String commentText;
    private Long userId;
    private Long productId;

    public CommentCreationRequestDto() {
        this(null, null, null);
    }

    public CommentCreationRequestDto(
            final String commentText,
            final Long userId,
            final Long productId
    ) {
        this.commentText = commentText;
        this.userId = userId;
        this.productId = productId;
    }
}
