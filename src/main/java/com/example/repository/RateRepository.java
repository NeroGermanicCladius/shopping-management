package com.example.repository;

import com.example.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rating, Long> {
}
