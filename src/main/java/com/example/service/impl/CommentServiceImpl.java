package com.example.service.impl;

import com.example.model.domain.Comment;
import com.example.model.domain.Product;
import com.example.model.domain.User;
import com.example.model.dto.CommentCreationRequestDto;
import com.example.exceptions.ResourceNotFoundException;
import com.example.repository.CommentRepository;
import com.example.repository.ProductRepository;
import com.example.service.CommentService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final ProductRepository productService;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(final ProductRepository productService, final CommentRepository commentRepository, final UserService userService) {
        this.productService = productService;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Comment addComment(CommentCreationRequestDto model) {
        Optional<Product> product = productService.findById(model.getProductId());
        if (product.isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + model.getProductId());
        }

        User user = userService.get(model.getUserId());

        final Comment comment = new Comment();
        comment.setCommentText(model.getCommentText());
        comment.setProduct(product.get());
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> ResourceNotFoundException.createInstance(Comment.class, "id:" + commentId));
    }
}
