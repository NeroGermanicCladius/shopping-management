package com.example.service.impl;


import com.example.domain.Product;
import com.example.domain.create.ProductCreationModel;
import com.example.domain.dto.ProductDto;
import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(final ProductCreationModel creationRequest) {
        final String name = creationRequest.getName();
        final Optional<Product> productOpt = productRepository.findByName(name);
        if (productOpt.isPresent()) {
            throw ResourceAlreadyExistsException.createInstance(Product.class, "name", name);
        }

        final Product product = new Product();
        product.setName(name);
        product.setPrice(creationRequest.getPrice());
        product.setOverview(creationRequest.getOverview());

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product update(Product productDto) {
        Optional<Product> updateProduct = productRepository.findById(productDto.getId());
        if (updateProduct.isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + productDto.getId());
        }

        final Product product = updateProduct.get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setOverview(productDto.getOverview());

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + id);
        }

        productRepository.deleteById(id);
        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> filteringData(ProductDto filter) {
        return productRepository.getFilteredData(filter);
    }

    @Override
    public Product get(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> ResourceNotFoundException.createInstance(Product.class, "id:" + productId));
    }
}
