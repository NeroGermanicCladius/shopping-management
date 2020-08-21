package com.example.service.impl;

import com.example.domain.Product;
import com.example.domain.Rating;
import com.example.domain.User;
import com.example.domain.create.RatingCreationModel;
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
    public RatingServiceImpl(ProductService productService, RateRepository rateRepository, UserService userService) {
        this.productService = productService;
        this.rateRepository = rateRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Rating rateProduct(RatingCreationModel model) {
        Optional<Product> product = Optional.ofNullable(productService.get(model.getProductId()));
        if (product.isEmpty()) {
            throw ResourceNotFoundException.createInstance(Product.class, "id:" + model.getProductId());
        }

        User user = userService.get(model.getUserId());

        final Rating rating = new Rating();
        rating.setProductId(product.get());
        rating.setRate(model.getRate());
        rating.setUserId(user);

        return rateRepository.save(rating);
    }
}
