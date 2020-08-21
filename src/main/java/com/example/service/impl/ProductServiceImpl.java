package com.example.service.impl;


import com.example.exceptions.NotImplementedException;
import com.example.model.domain.Product;
import com.example.model.dto.ProductCreationRequestDto;
import com.example.model.dto.ProductDto;
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
    public Product create(final ProductCreationRequestDto creationRequest) {
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
        final Product product = get(productDto.getId());

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setOverview(productDto.getOverview());

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product delete(Long id) {
        final Product product = get(id);
        productRepository.deleteById(id);
        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> filteringData(ProductDto filter) {
        final List<Product> filteredData = productRepository.getFilteredData(filter);

        if (true) {
            throw new NotImplementedException("12 hours, you know...");
        }

        return filteredData;
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(Long id) {
        return find(id)
                .orElseThrow(() -> ResourceNotFoundException.createInstance(Product.class, "id:" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> find(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public void makeSureProductExists(Long id) {
        if (find(id).isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + id);
        }
    }
}
