package com.example.service;

import com.example.domain.Rating;
import com.example.domain.create.RatingCreationModel;

public interface RatingService {
    Rating rateProduct(RatingCreationModel model);
}
