package com.akinovapp.domain.dao;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {

    private Long Id;

    private String productName;

    private Long productNumber;

    private Long price;

    private Long quantity;

    private String companyName;

    private Date dateListed;

    private Boolean deleteStatus;
}
