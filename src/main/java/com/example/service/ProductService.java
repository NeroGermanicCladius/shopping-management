package com.example.service;

import com.example.model.domain.Product;
import com.example.model.dto.ProductCreationRequestDto;
import com.example.model.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(ProductCreationRequestDto creationRequest);

    List<Product> getAll();

    Product update(Product product);

    Product delete(Long id);

    List<Product> filteringData(ProductDto filter);

    Product get(Long id);

    Optional<Product> find(Long id);

    void makeSureProductExists(Long id);
}
