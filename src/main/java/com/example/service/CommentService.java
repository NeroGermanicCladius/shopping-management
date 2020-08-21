package com.example.service;

import com.example.domain.Comment;
import com.example.domain.create.CommentCreationModel;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentCreationModel comment);

    List<Comment> getAll();

    Comment getCommentById(Long commentId);
}
