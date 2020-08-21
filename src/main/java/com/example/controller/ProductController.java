package com.example.controller;

import com.example.domain.Product;
import com.example.domain.create.ProductCreationModel;
import com.example.domain.dto.ProductDto;
import com.example.mapper.MapperUtils;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Api(value = "Products", tags = "Products")
@RestController
@RequestMapping("/api/product")
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
    public ResponseEntity<List<Product>> getAllProducts() {
        LOGGER.debug("Getting all products");
        List<Product> allProducts = productService.getAll();

        LOGGER.info("Done getting all products");
        return ResponseEntity.ok(allProducts);
    }

    @ApiOperation(
            value = "Create a product.",
            nickname = "createProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/create")
    public ResponseEntity<Product> create(@Valid @NotNull @RequestBody ProductCreationModel product) {
        LOGGER.debug("Creating product");
        Product createdProduct = productService.create(product);

        LOGGER.debug("Done creating product");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiOperation(
            value = "Update a product.",
            nickname = "updateProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @PutMapping("/update")
    public ResponseEntity<ProductDto> update(
            @Valid @NotNull @RequestBody Product product) {
        LOGGER.debug("Updating concrete product");

        Optional<Product> updatedProduct = Optional.ofNullable(productService.update(product));
        if (!updatedProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LOGGER.debug("Done updating concrete product");
        return ResponseEntity.ok(MapperUtils.map(updatedProduct.get(), ProductDto.class));
    }

    @ApiOperation(
            value = "Delete a product.",
            nickname = "deleteProduct",
            authorizations = {@Authorization(value = "basicAuth")})
    @DeleteMapping("{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Deleting concrete product");

        Optional<Product> product = productService.delete(id);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LOGGER.debug("Done deleting concrete product");
        return ResponseEntity.ok(MapperUtils.map(product.get(), ProductDto.class));
    }

    @ApiOperation(
            value = "Get filtered data.",
            nickname = "getAllFilteredData",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/getFilteredData")
    public ResponseEntity<List<Product>> filteringData(
            @Valid @NotNull @RequestBody ProductDto filter) {
        LOGGER.debug("Getting filtered data");
        List<Product> allProducts = productService.filteringData(filter);

        LOGGER.info("Done getting filtered data");
        return ResponseEntity.ok(allProducts);
    }
}
