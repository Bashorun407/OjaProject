package com.akinovapp.domain.dao;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {

    private Long Id;

    private String productName;

    private Long productNumber;

    private String companyName;

    private Long customerId;

    private Long amount;

    private Boolean paymentStatus;

    private Long transactionNumber;

    private Date transactionDate;
}
