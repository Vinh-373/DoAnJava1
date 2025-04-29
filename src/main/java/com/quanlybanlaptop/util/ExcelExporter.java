package com.quanlybanlaptop.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Lớp tiện ích xuất Excel cho mọi loại dữ liệu dạng bảng.
 */
public class ExcelExporter {

    /**
     * Xuất dữ liệu ra file Excel (.xlsx)
     * @param headers Danh sách tiêu đề cột
     * @param data Dữ liệu từng dòng, mỗi dòng là một List<Object>
     * @param sheetName Tên sheet trong file Excel
     * @param filePath Đường dẫn file muốn lưu
     * @throws IOException Nếu có lỗi khi ghi file
     */
    public static void exportToExcel(List<String> headers, List<List<Object>> data, String sheetName, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName != null ? sheetName : "Sheet1");

        // Ghi header
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < headers.size(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(headers.get(col));
        }

        // Ghi dữ liệu
        for (int row = 0; row < data.size(); row++) {
            Row excelRow = sheet.createRow(row + 1);
            List<Object> rowData = data.get(row);
            for (int col = 0; col < rowData.size(); col++) {
                Cell cell = excelRow.createCell(col);
                Object value = rowData.get(col);
                cell.setCellValue(value != null ? value.toString() : "");
            }
        }

        // Auto size cột
        for (int col = 0; col < headers.size(); col++) {
            sheet.autoSizeColumn(col);
        }

        // Ghi file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}