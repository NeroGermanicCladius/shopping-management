package com.example.controller;

import com.example.model.domain.Product;
import com.example.model.dto.ProductCreationRequestDto;
import com.example.model.dto.ProductDto;
import com.example.model.mapper.MapperUtils;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Api(value = "Products", tags = "Products")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(
            value = "Get all products.",
            nickname = "getAllProducts",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        LOGGER.debug("Getting all products");

        final List<Product> products = productService.getAll();
        final List<ProductDto> productDtos = MapperUtils.mapAll(products, ProductDto.class);

        LOGGER.info("Done getting all products");
        return ResponseEntity.ok(productDtos);
    }

    @ApiOperation(
            value = "Create a product.",
            nickname = "createProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@Valid @NotNull @RequestBody ProductCreationRequestDto creationRequestDto) {
        LOGGER.debug("Creating product");

        final Product createdProduct = productService.create(creationRequestDto);
        final ProductDto createdProductDto = MapperUtils.map(createdProduct, ProductDto.class);

        LOGGER.debug("Done creating product");
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Update a product.",
            nickname = "updateProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @PutMapping("/update")
    public ResponseEntity<ProductDto> update(
            @Valid @NotNull @RequestBody Product product) {
        LOGGER.debug("Updating concrete product");

        final Product updatedProduct = productService.update(product);
        final ProductDto updatedProductDto = MapperUtils.map(updatedProduct, ProductDto.class);

        LOGGER.debug("Done updating concrete product");
        return ResponseEntity.ok(updatedProductDto);
    }

    @ApiOperation(
            value = "Delete a product.",
            nickname = "deleteProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @DeleteMapping("{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Deleting concrete product");

        final Product deletedProduct = productService.delete(id);
        final ProductDto deletedProductDto = MapperUtils.map(deletedProduct, ProductDto.class);

        LOGGER.debug("Done deleting concrete product");
        return ResponseEntity.ok(deletedProductDto);
    }

    @ApiOperation(
            value = "Get filtered data.",
            nickname = "getAllFilteredData",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/getFilteredData")
    public ResponseEntity<List<ProductDto>> filteringData(
            @Valid @NotNull @RequestBody ProductDto filter) {
        LOGGER.debug("Getting filtered data");

        final List<Product> foundProducts = productService.filteringData(filter);
        final List<ProductDto> foundProductDtos = MapperUtils.mapAll(foundProducts, ProductDto.class);

        LOGGER.info("Done getting filtered data");
        return ResponseEntity.ok(foundProductDtos);
    }
}
