package com.example.controller;

import com.example.configuration.UserDetailsImpl;
import com.example.domain.Product;
import com.example.domain.Rating;
import com.example.domain.User;
import com.example.domain.create.RatingCreationModel;
import com.example.service.ProductService;
import com.example.service.RatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Api(value = "Rates", tags = "Rates")
@RestController
@RequestMapping("/api/rating")
public class RatingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final RatingService ratingService;
    private final ProductService productService;

    @Autowired
    public RatingController(RatingService ratingService, ProductService productService) {
        this.ratingService = ratingService;
        this.productService = productService;
    }

    @ApiOperation(
            value = "Add comment to concrete product",
            nickname = "addComment",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/addComment")
    public ResponseEntity<Rating> rateProduct(
            @Valid @NotNull @RequestBody final RatingCreationModel model) {
        LOGGER.debug("Rating product");

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
        Rating rating = ratingService.rateProduct(model);

        LOGGER.debug("Done getting concrete comment");
        return ResponseEntity.ok(rating);
    }
}
