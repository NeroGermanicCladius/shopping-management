package com.example.repository;

import com.example.model.domain.Product;
import com.example.model.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Procedure(value = "GET_PRODUCT_FILTERED")
    List<Product> getFilteredData(ProductDto filter);
}
