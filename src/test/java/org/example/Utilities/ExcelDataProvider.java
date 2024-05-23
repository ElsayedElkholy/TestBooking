package org.example.Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelDataProvider {

    public static Map<String, String> getRowData(String filePath, String sheetName, int rowNumber) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNumber);
            Row headerRow = sheet.getRow(0);

            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                Cell headerCell = headerRow.getCell(cellNum);
                data.put(headerCell.getStringCellValue(), cell.getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
