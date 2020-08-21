package com.example.service;

import com.example.domain.Product;
import com.example.domain.create.ProductCreationModel;
import com.example.domain.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(ProductCreationModel creationRequest);

    List<Product> getAll();

    Product update(Product product);

    Optional<Product> delete(Long id);

    List<Product> filteringData(ProductDto filter);

    Product get(Long productId);
}
