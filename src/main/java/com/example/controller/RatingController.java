package com.example.controller;

import com.example.configuration.security.UserPrincipal;
import com.example.model.domain.Rating;
import com.example.model.dto.RatingCreationRequestDto;
import com.example.model.dto.RatingDto;
import com.example.model.mapper.MapperUtils;
import com.example.service.ProductService;
import com.example.service.RatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@Api(value = "Rates", tags = "Rates")
@RestController
@RequestMapping("/api/v1/rating")
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
            value = "Add rate to concrete product",
            nickname = "addRating",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/addRating")
    public ResponseEntity<RatingDto> rateProduct(
            @Valid @NotNull @RequestBody final RatingCreationRequestDto creationRequestDto,
            final UserPrincipal principal) {
        LOGGER.debug("Rating product");

        productService.makeSureProductExists(creationRequestDto.getProductId());

        creationRequestDto.setUserId(principal.getUser().getId());
        final Rating rating = ratingService.rateProduct(creationRequestDto);
        final RatingDto ratingDto = MapperUtils.map(rating, RatingDto.class);

        LOGGER.debug("Done getting concrete rating");
        return ResponseEntity.ok(ratingDto);
    }
}
