package com.akinovapp.controller.ExcelController;

import com.akinovapp.excelWriter.excelWriter.CustomerExcel.CustomerExcelWriter;
import com.akinovapp.excelWriter.excelWriter.ProductExcel.ProductExcelWriter;
import com.akinovapp.excelWriter.excelWriter.RatingExcel.RatingExcelWriter;
import com.akinovapp.excelWriter.excelWriter.ShopExcel.ShopExcelWriter;
import com.akinovapp.excelWriter.excelWriter.TransactionExcel.TransactionExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excelWriter")
public class ExcelWriterApi {
    @Autowired
    private CustomerExcelWriter customerExcelWriter;

    @Autowired
    private ProductExcelWriter productExcelWriter;

    @Autowired
    private RatingExcelWriter ratingExcelWriter;

    @Autowired
    private ShopExcelWriter shopExcelWriter;

    @Autowired
    private TransactionExcelWriter transactionExcelWriter;

    //(1) Customer Excel
    @GetMapping("/writeCustomer")
    public void writeCustExcel (){
        customerExcelWriter.writeCustExcel();
    }

    //(2) Product Excel
    @GetMapping("/writeProduct")
    public void writeProdcutExcel(){
        productExcelWriter.writeProdcutExcel();
    }

    //(3) Rating Excel
    @GetMapping("/writeRating")
    public void writeRatingExcel(){
        ratingExcelWriter.writeRatingExcel();
    }

    //(4) Shop Excel
    @GetMapping("/writeShop")
    public void writeShopExcel(){
        shopExcelWriter.writeShopExcel();
    }

    //(5) Transaction Excel
    @GetMapping("/writeTransaction")
    public void writeTransactionExcel(){
        transactionExcelWriter.writeTransactionExcel();
    }
}
