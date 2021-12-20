package com.akinovapp.domain.dao;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ShopDto {

    private Long Id;

    private String companyName;

    private String productName;

    private Long shopNumber;

    private Long price;

    private Long quantity;

    private Long phoneNumber;

    private Date dateListed;

    private String country;

    private Boolean deletedStatus;
}
