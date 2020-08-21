package com.example.controller;

import com.example.configuration.UserDetailsImpl;
import com.example.domain.Comment;
import com.example.domain.Product;
import com.example.domain.User;
import com.example.domain.create.CommentCreationModel;
import com.example.domain.dto.CommentDto;
import com.example.mapper.MapperUtils;
import com.example.service.CommentService;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Api(value = "Comments", tags = "Comments")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final ProductService productService;

    @Autowired
    public CommentController(CommentService commentService, ProductService productService) {
        this.commentService = commentService;
        this.productService = productService;
    }

    @ApiOperation(
            value = "Get all comments.",
            nickname = "getAllComments",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllProducts() {
        LOGGER.debug("Getting all comments");

        List<Comment> all = commentService.getAll();

        LOGGER.info("Done getting all comments");
        return ResponseEntity.ok(all);
    }

    @ApiOperation(
            value = "Get a user by id.",
            nickname = "getUserById",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("{id}")
    public ResponseEntity<CommentDto> get(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Getting concrete comment");
        Optional<Comment> commentById = Optional.ofNullable(commentService.getCommentById(id));
        if (!commentById.isPresent()) {
            LOGGER.debug("Not found concrete comment");
            return ResponseEntity.notFound().build();
        }

        final CommentDto result = MapperUtils.map(commentById.get(), CommentDto.class);

        LOGGER.debug("Done getting concrete comment");
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "Add comment to concrete product",
            nickname = "addComment",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(
            @Valid @NotNull @RequestBody final CommentCreationModel model) {
        LOGGER.debug("Adding comment");

        Optional<Product> product = Optional.ofNullable(productService.get(model.getProductId()));
        if (!product.isPresent()) {
            LOGGER.debug("Not found concrete product");
            return ResponseEntity.notFound().build();
        }

        User creator =
                ((UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUser();

        model.setUserId(creator.getId());

        Comment comment = commentService.addComment(model);

        LOGGER.debug("Done getting concrete comment");
        return ResponseEntity.ok(comment);
    }
}
