package com.akinovapp.domain.dao;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {

    private Long Id;

    private  String firstName;

    private  String lastName;

    private Long customerNumber;

    private String email;

    private Long phoneNumber;

    private Long accountBalance;

    private String country;

    private Boolean deletedStatus;

    private Date dateCreated;
}
