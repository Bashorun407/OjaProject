package com.akinovapp.repository;

import com.akinovapp.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingReppo extends JpaRepository<Rating, Long> {
}
