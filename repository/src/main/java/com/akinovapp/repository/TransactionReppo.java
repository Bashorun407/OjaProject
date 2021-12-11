package com.akinovapp.repository;

import com.akinovapp.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionReppo extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionByTransactionNumber(Long transactionNumber);
    List<Transaction> findTransactionByTransactionDate(Date transactionDate);

}
