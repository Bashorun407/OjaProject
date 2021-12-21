package com.akinovapp.domain.dao;

import lombok.Data;

@Data
public class RatingDto {

    private Long Id;

    private Long productNumber;

    private String productName;

    private Long rating;

    private Long reviews;
}
