package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Ojah_Product_Rating")
public class Rating {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Rating_ID")
    private Long Id;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Rating")
    private Long rating;

    @Column(name = "Reviews")
    private Long reviews;

}
