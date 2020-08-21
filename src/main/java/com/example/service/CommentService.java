package com.example.service;

import com.example.model.domain.Comment;
import com.example.model.dto.CommentCreationRequestDto;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentCreationRequestDto comment);

    List<Comment> getAll();

    Comment getCommentById(Long commentId);
}
