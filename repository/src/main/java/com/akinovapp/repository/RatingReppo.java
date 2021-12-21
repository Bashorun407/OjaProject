package com.akinovapp.repository;

import com.akinovapp.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingReppo extends JpaRepository<Rating, Long> {

    Optional<Rating> findRatingByProductNumber(Long productNumber);
}
