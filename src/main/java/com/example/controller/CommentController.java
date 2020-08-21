package com.example.controller;

import com.example.configuration.security.UserPrincipal;
import com.example.model.domain.Comment;
import com.example.model.dto.CommentCreationRequestDto;
import com.example.model.dto.CommentDto;
import com.example.model.mapper.MapperUtils;
import com.example.service.CommentService;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Api(value = "Comments", tags = "Comments")
@RestController
@RequestMapping("/api/v1/comment")
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
    public ResponseEntity<List<CommentDto>> getAllProducts() {
        LOGGER.debug("Getting all comments");

        final List<Comment> allComments = commentService.getAll();
        final List<CommentDto> allCommentsDtos = MapperUtils.mapAll(allComments, CommentDto.class);

        LOGGER.info("Done getting all comments");
        return ResponseEntity.ok(allCommentsDtos);
    }

    @ApiOperation(
            value = "Get a user by id.",
            nickname = "getUserById",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("{id}")
    public ResponseEntity<CommentDto> get(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Getting concrete comment");

        final Comment comment = commentService.getCommentById(id);
        final CommentDto result = MapperUtils.map(comment, CommentDto.class);

        LOGGER.debug("Done getting concrete comment");
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "Add comment to concrete product",
            nickname = "addComment",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/addComment")
    public ResponseEntity<CommentDto> addComment(
            @Valid @NotNull @RequestBody final CommentCreationRequestDto creationRequestDto,
            final UserPrincipal principal) {
        LOGGER.debug("Adding comment");

        productService.makeSureProductExists(creationRequestDto.getProductId());

        creationRequestDto.setUserId(principal.getUser().getId());

        final Comment comment = commentService.addComment(creationRequestDto);
        final CommentDto commentDto = MapperUtils.map(comment, CommentDto.class);

        LOGGER.debug("Done getting concrete comment");
        return ResponseEntity.ok(commentDto);
    }
}
