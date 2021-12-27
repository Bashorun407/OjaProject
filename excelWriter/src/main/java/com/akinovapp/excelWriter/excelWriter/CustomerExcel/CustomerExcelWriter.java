package com.akinovapp.excelWriter.excelWriter.CustomerExcel;

import com.akinovapp.domain.entity.Customer;
import com.akinovapp.repository.CustomerReppo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class CustomerExcelWriter {

    @Autowired
    private CustomerReppo customerReppo;

    public void writeCustExcel (){

        try {
            List<Customer> customerList = customerReppo.findAll();

            String filePath = ".\\Book1.xlsx";

            //To check that the filePath is valid
            if(!StringUtils.hasText(filePath))
                throw new FileNotFoundException("File not found");


            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            //Creating an Excel Work book
            XSSFWorkbook workbook = new XSSFWorkbook();

            //Creating a sheet on the workbook above
            XSSFSheet sheet = workbook.createSheet("Customer Sheet");

            //Setting Font to determine the font parameters of all entries on the workbook
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(12);

            //Setting CellStyle to style the cells
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            cellStyle.setFont(font);

            //Create rows
            XSSFRow row = sheet.createRow(0);

            //Create Cells in the row
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("ID");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue("First Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue("Last Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue("Customer Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue("Email Address");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue("Phone Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue("Account Balance");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(6);

            cell = row.createCell(7);
            cell.setCellValue("Country");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(7);

            cell = row.createCell(8);
            cell.setCellValue("Deleted Status");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(8);

            cell = row.createCell(9);
            cell.setCellValue("Date Created");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(9);


            //USINIG FOR LOOP TO ACCESS AND PRINT THE CONTENTS OF THE CUSTOMER TABLE
            int rowCount = 1;

            for(Customer customer: customerList){

                //Creating a new Row for each customer
                row = sheet.createRow(rowCount++);

                //Setting the contents of the cells
                cell = row.createCell(0);
                cell.setCellValue(customer.getId());

                cell = row.createCell(1);
                cell.setCellValue(customer.getFirstName());

                cell = row.createCell(2);
                cell.setCellValue(customer.getLastName());

                cell = row.createCell(3);
                cell.setCellValue(customer.getCustomerNumber());

                cell = row.createCell(4);
                cell.setCellValue(customer.getEmail());

                cell = row.createCell(5);
                cell.setCellValue(customer.getPhoneNumber());

                cell = row.createCell(6);
                cell.setCellValue(customer.getAccountBalance());

                cell = row.createCell(7);
                cell.setCellValue(customer.getCountry());

                cell = row.createCell(8);
                cell.setCellValue(customer.getDeletedStatus());

                cell = row.createCell(9);
                cell.setCellValue(customer.getDateCreated());

                //Writing the data into the file location on the Output Stream
                workbook.write(fileOutputStream);
//                fileOutputStream.close();
//                workbook.close();

                System.out.println("Customer Excel File is successfully Written");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
