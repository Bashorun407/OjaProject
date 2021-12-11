package com.akinovapp.controller.TransactionController;

import com.akinovapp.domain.entity.Transaction;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.service.transactionservice.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {

    @Autowired
    private TransactionService transactionService;

    //(1) Method to select Item(s)
    @PostMapping("/purchase")
    public ResponsePojo<Transaction> buyItem(@RequestParam String productName,
                                             @RequestParam String companyName,
                                             @RequestParam Long quantity,
                                             @RequestParam Long customerId,
                                             @RequestParam String email
                                                )
    {

        return transactionService.buyItem(productName, companyName, quantity, customerId, email);
    }


    //(2) Method to list all successful transactions
    @GetMapping("/allTransactions")
    public ResponsePojo<List<Transaction>> getAllTransaction(){

        return transactionService.getAllTransaction();
    }

    //(3) Method to get a specific Transaction
    @GetMapping("/getTransaction/{transactionNumber}")
    public ResponsePojo<Transaction> getTransactionByTransactionNumber(@PathVariable Long transactionNumber){

        return transactionService.getTransactionByTransactionNumber(transactionNumber);
    }


    //(4) Method to get a list of transaction on a specific date
    @GetMapping("/transactionOnDate/{date}")
    public ResponsePojo<List<Transaction>> getTransactionOnADate(@PathVariable Date date){

        return transactionService.getTransactionOnADate(date);
    }
    }
