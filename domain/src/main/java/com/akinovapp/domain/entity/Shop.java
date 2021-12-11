package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Oja-Shop")
public class Shop {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Shop_ID")
    private Long Id;

    @Column(name = "Company_Name")
    private String companyName;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Price")
    private Long price;

    @Column(name = "Quantity_Available")
    private Long quantity;

    @Column(name = "Phone_Number")
    private Long phoneNumber;

    @Column(name = "Date_Listed")
    private Date dateListed;

    @Column(name = "Country")
    private String country;

    @Column(name = "Deleted_Status")
    private Boolean deletedStatus;
}
