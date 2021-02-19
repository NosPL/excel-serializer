package com.noscompany.excel.converter;

import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

@AllArgsConstructor
public class ExcelSerializer {
    private final Config config;
    private final Workbook workbook = new HSSFWorkbook();
    private final Sheet sheet = workbook.createSheet();
    private final CellStyle cellStyle = cellStyle();

    public void serialize(Set<CellEntry> cellEntries) {
        cellEntries.forEach(this::setCell);
    }

    private void setCell(CellEntry cellEntry) {
        CellAddress cellAddress = cellEntry.getCellAddress();
        int row = cellAddress.getRow();
        int column = cellAddress.getColumn();
        String cellValue = cellEntry.getCellValue();
        Cell cell = getRow(row).createCell(column);
        cell.setCellValue(cellValue);
        cell.setCellStyle(cellStyle);
    }

    public void saveToFile() {
        try (OutputStream out = new FileOutputStream(config.getFile())) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Row getRow(int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null)
            row = sheet.createRow(rowNum);
        return row;
    }

    private CellStyle cellStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }
}
