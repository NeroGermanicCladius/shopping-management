package com.example.service;

import com.example.model.domain.Rating;
import com.example.model.dto.RatingCreationRequestDto;

public interface RatingService {
    Rating rateProduct(RatingCreationRequestDto model);
}
