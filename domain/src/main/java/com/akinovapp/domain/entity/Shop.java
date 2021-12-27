package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "Oja-Shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Shop_ID", unique = true)
    private Long Id;

    @Column(name = "Company_Name", nullable = false)
    private String companyName;

    @Column(name = "Shop_Number", unique = true, nullable = false)
    private Long shopNumber;

    @Column(name = "Phone_Number")
    private Long phoneNumber;

    @Column(name = "Date_Listed")
    private Date dateListed;

    @Column(name = "Country")
    private String country;

    @Column(name = "Deleted_Status")
    private Boolean deletedStatus;

    @OneToMany
    @Column(name = "Product_List")
    private List<Product> products;
}
