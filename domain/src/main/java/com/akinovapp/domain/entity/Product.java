package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Oja_Product")
public class Product {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Product_ID", unique = true)
    private Long Id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Price")
    private Long price;

    @Column(name = "Quantity_Available")
    private Long quantity;

    @Column( name = "Company_Name")
    private String companyName;

    @Column(name = "Date_Listed")
    private Date dateListed;

    @Column(name = "Delete_Status")
    private Boolean deleteStatus;
}
