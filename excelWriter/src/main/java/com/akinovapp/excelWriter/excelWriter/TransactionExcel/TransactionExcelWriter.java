package com.akinovapp.excelWriter.excelWriter.TransactionExcel;

import com.akinovapp.domain.entity.Transaction;
import com.akinovapp.repository.TransactionReppo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class TransactionExcelWriter {

    @Autowired
    private TransactionReppo transactionReppo;

    public void writeTransactionExcel(){

        try{
            List<Transaction> transactionList = transactionReppo.findAll();
            if(transactionList.isEmpty())
                throw new FileNotFoundException("There is no record of any transaction yet.");

            String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Oja\\excelWriter\\src\\main\\java\\com\\akinovapp\\excelWriter\\TransactionExcel\\Book1.xlsx";

            FileOutputStream outputStream = new FileOutputStream(filePath);

            //Creating Excel Workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            //Setting the Font
            XSSFFont font = workbook.createFont();
            font.setFontHeight(16);
            font.setBold(true);

            //Setting Cell Style
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(IndexedColors.PALE_BLUE.getIndex());
            cellStyle.setFont(font);

            XSSFSheet sheet = workbook.createSheet("Transaction Sheet");
            XSSFRow  row = sheet.createRow(0);

            //Creating Header Cell Entries
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Transaction ID");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue("Product Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue("Product Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue("Company Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue("Customer ID");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue("Amount");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue("Payment Status");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(6);

            cell = row.createCell(7);
            cell.setCellValue("Transaction Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(7);

            cell = row.createCell(8);
            cell.setCellValue("Transaction Date");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(8);

            //Using FOR LOOP to print the contents of the transactionList in to the EXCEL file in the specified location
            int rowCount= 1;

            for(Transaction transaction: transactionList){

                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(transaction.getId());

                cell = row.createCell(1);
                cell.setCellValue(transaction.getProductName());

                cell = row.createCell(2);
                cell.setCellValue(transaction.getProductNumber());

                cell = row.createCell(3);
                cell.setCellValue(transaction.getCompanyName());

                cell = row.createCell(4);
                cell.setCellValue(transaction.getCustomerId());

                cell = row.createCell(5);
                cell.setCellValue(transaction.getAmount());

                cell = row.createCell(6);
                cell.setCellValue(transaction.getPaymentStatus());

                cell = row.createCell(7);
                cell.setCellValue(transaction.getTransactionNumber());

                cell = row.createCell(8);
                cell.setCellValue(transaction.getTransactionDate());
            }

            //Workbook writing the data into the outputstream
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();

            System.out.println("Transaction Excel File Written Successfully");

        }catch (Exception e){

            e.printStackTrace();

        }

    }
}
