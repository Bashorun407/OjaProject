package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Oja_Customer")
public class Customer {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Customer_ID", unique = true, nullable = false)
    private Long Id;

    @Column(name = "First_Name", nullable = false)
    private  String firstName;

    @Column(name = "Last_Name", nullable = false)
    private  String lastName;

    @Column(name = "Customer_Number", unique = true, nullable = false)
    private Long customerNumber;

    @Column(name = "Email_Address", unique = true, nullable = false)
    private String email;

    @Column(name = "Phone_Number", unique = true)
    private Long phoneNumber;

    @Column(name = "Account_Balance")
    private Long accountBalance;

    @Column(name = "Country")
    private String country;

    @Column(name = "Deleted_Status")
    private Boolean deletedStatus;

    @Column(name = "Date_Created")
    private Date dateCreated;
}
