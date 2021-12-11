package com.akinovapp.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Oja_Transaction")
public class Transaction {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Transaction_ID")
    private Long Id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Company_Name")
    private String companyName;

    @Column(name = "Customer_Id")
    private Long customerId;

    @Column(name = "Amount")
    private Long amount;

    @Column(name = "Payment_Status")
    private Boolean paymentStatus;

    @Column(name = "Transaction_Number")
    private Long transactionNumber;

    @Column(name = "Transaction_Date")
    private Date transactionDate;

}
