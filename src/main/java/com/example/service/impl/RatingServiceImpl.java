package com.example.service.impl;

import com.example.model.domain.Product;
import com.example.model.domain.Rating;
import com.example.model.domain.User;
import com.example.model.dto.RatingCreationRequestDto;
import com.example.exceptions.ResourceNotFoundException;
import com.example.repository.RateRepository;
import com.example.service.ProductService;
import com.example.service.RatingService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final ProductService productService;
    private final RateRepository rateRepository;
    private final UserService userService;

    @Autowired
    public RatingServiceImpl(final ProductService productService, final RateRepository rateRepository, final UserService userService) {
        this.productService = productService;
        this.rateRepository = rateRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Rating rateProduct(RatingCreationRequestDto model) {
        Optional<Product> product = Optional.ofNullable(productService.get(model.getProductId()));
        if (product.isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + model.getProductId());
        }

        User user = userService.get(model.getUserId());

        final Rating rating = new Rating();
        rating.setProduct(product.get());
        rating.setRate(model.getRate());
        rating.setUser(user);

        return rateRepository.save(rating);
    }
}
