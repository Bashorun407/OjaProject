package com.akinovapp.excelWriter.excelWriter.RatingExcel;

import com.akinovapp.domain.entity.Rating;
import com.akinovapp.repository.RatingReppo;
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
public class RatingExcelWriter {

    @Autowired
    private RatingReppo ratingReppo;

    public void writeRatingExcel(){

        try{

            List<Rating> ratingList = ratingReppo.findAll();
            if(ratingList.isEmpty())
                throw new FileNotFoundException("There are no rating yet.");

            String filePath = ".\\Book3.xlsx";

            FileOutputStream outputStream = new FileOutputStream(filePath);

            //Creating Excel workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet  = workbook.createSheet("Rating Sheet");

            //Setting Font
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);

            //Setting Cell Style
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            cellStyle.setFont(font);

            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Rating ID");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue("Product Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue("Product Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue("Rating");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue("Reviews");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(4);

            //Using FOR LOOP to print the contents of RatingList to the specified filePath
            int rowCount = 1;

            for(Rating rating : ratingList){

                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(rating.getId());

                cell = row.createCell(1);
                cell.setCellValue(rating.getProductNumber());

                cell = row.createCell(2);
                cell.setCellValue(rating.getProductName());

                cell = row.createCell(3);
                cell.setCellValue(rating.getRating());

                cell = row.createCell(4);
                cell.setCellValue(rating.getReviews());

            }

            //Workbook writing the data to the outputStream
            workbook.write(outputStream);
//            outputStream.close();
//            workbook.close();

            System.out.println("Rating Excel File Written Successfully");

        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
