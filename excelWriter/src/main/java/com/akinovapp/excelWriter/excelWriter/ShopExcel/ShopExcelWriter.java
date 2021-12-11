package com.akinovapp.excelWriter.excelWriter.ShopExcel;

import com.akinovapp.domain.entity.Shop;
import com.akinovapp.repository.ShopReppo;
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
public class ShopExcelWriter {

    @Autowired
    private ShopReppo shopReppo;
    public void writeShopExcel(){

        try {

            List<Shop> shopList = shopReppo.findAll();
            if(shopList.isEmpty())
                throw new FileNotFoundException("There is no shop in the repository.");

            String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Oja\\excelWriter\\src\\main\\java\\com\\akinovapp\\excelWriter\\ShopExcel\\Book1.xlsx";
            FileOutputStream outputStream = new FileOutputStream(filePath);

            //Creating an Excel file workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            //Setting Font
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);

            //Setting CellSyles
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
            cellStyle.setFont(font);

            //Creating sheet
            XSSFSheet sheet = workbook.createSheet(" Shop Sheet");

            //Creating the first row on the sheet
            XSSFRow row = sheet.createRow(0);

            //Creating the cells where header contents will be written
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Shop ID");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue("Company Name");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue("Product Name");
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
            cell.setCellValue("Phone Number");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue("Date Listed");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(6);

            cell = row.createCell(7);
            cell.setCellValue("Country");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(7);

            cell = row.createCell(8);
            cell.setCellValue("Delete Status");
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(8);

            //USING FOR LOOP to write the contents of the ShopList to the specified file location
            int rowCount = 1;
            for(Shop shop : shopList){

                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(shop.getId());

                cell = row.createCell(1);
                cell.setCellValue(shop.getCompanyName());

                cell = row.createCell(2);
                cell.setCellValue(shop.getProductName());

                cell = row.createCell(3);
                cell.setCellValue(shop.getPrice());

                cell = row.createCell(4);
                cell.setCellValue(shop.getQuantity());

                cell = row.createCell(5);
                cell.setCellValue(shop.getPhoneNumber());

                cell = row.createCell(6);
                cell.setCellValue(shop.getDateListed());

                cell = row.createCell(7);
                cell.setCellValue(shop.getCountry());

                cell = row.createCell(8);
                cell.setCellValue(shop.getDeletedStatus());
            }

            //writing to specified location
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();

            System.out.println(" Shop Excel File written successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
