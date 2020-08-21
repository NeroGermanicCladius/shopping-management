package com.example.repository;

import com.example.domain.Product;
import com.example.domain.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Procedure(value = "GET_PRODUCT_FILTERED")
    List<Product> getFilteredData(ProductDto filter);
}
