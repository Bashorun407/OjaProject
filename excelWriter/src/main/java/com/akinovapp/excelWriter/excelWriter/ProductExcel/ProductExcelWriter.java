package com.akinovapp.excelWriter.excelWriter.ProductExcel;

import com.akinovapp.domain.entity.Product;
import com.akinovapp.repository.ProductReppo;
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
public class ProductExcelWriter {

    @Autowired
    private ProductReppo productReppo;

    public void writeProdcutExcel(){

        try {
            List<Product> productList = productReppo.findAll();
            if(productList.isEmpty())
                throw new FileNotFoundException("Product Repository is empty");

        String filePath = ".\\Book2.xlsx";
        if(StringUtils.hasText(filePath))
            throw new FileNotFoundException(" File specified is not found");

        //Using FileOutputStream to access the file location
            FileOutputStream outputStream = new FileOutputStream(filePath);

            //Creating Excel Workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Product Sheet");

            //Setting Font
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);

            //Setting CellStyle
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
            cellStyle.setFont(font);

            //Setting the Header row
            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Product ID");
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
            cell.setCellValue("Price");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue("Quantity");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue("Company Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue("Date Listed");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(6);

            cell = row.createCell(7);
            cell.setCellValue("Deleted Status");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(7);


            int rowCount = 1;
            //Using FOR LOOP to access the contents of the Product repository
            for(Product product : productList){

                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(product.getId());

                cell = row.createCell(1);
                cell.setCellValue(product.getProductName());

                cell = row.createCell(2);
                cell.setCellValue(product.getProductNumber());

                cell = row.createCell(3);
                cell.setCellValue(product.getPrice());

                cell = row.createCell(4);
                cell.setCellValue(product.getQuantity());

                cell = row.createCell(5);
                cell.setCellValue(product.getCompanyName());

                cell = row.createCell(6);
                cell.setCellValue(product.getDateListed());

                cell = row.createCell(7);
                cell.setCellValue(product.getDeleteStatus());
            }

            //Writing the data into the file location on the Output Stream
            workbook.write(outputStream);
//            outputStream.close();
//            workbook.close();

            System.out.println("Product Excel file is written successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
