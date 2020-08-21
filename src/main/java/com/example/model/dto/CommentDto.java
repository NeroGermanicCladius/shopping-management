package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commentText;
    private Long userId;
    private Long productId;

    public CommentDto() {
        this(null, null, null, null);
    }

    @JsonCreator
    public CommentDto(
            @JsonProperty("id") final Long id,
            @JsonProperty("commentText") final String commentText,
            @JsonProperty("userId") final Long userId,
            @JsonProperty("productId") final Long productId
    ) {
        this.id = id;
        this.commentText = commentText;
        this.userId = userId;
        this.productId = productId;
    }
}
