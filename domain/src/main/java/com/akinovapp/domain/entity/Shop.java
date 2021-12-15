package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Oja-Shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Shop_ID", unique = true)
    private Long Id;

    @Column(name = "Company_Name", unique = true, nullable = false)
    private String companyName;

    @Column(name = "Product_Name", nullable = false)
    private String productName;

    @Column(name = "Price", nullable = false)
    private Long price;

    @Column(name = "Quantity_Available", nullable = false)
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
