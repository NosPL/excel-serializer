package com.noscompany.excel.client;

import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.commons.SurfaceSize;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelClient {
    private final XSSFWorkbook workbook;
    private final org.apache.poi.ss.usermodel.Sheet sheet;
    private final CellStyles cellStyles;

    public ExcelClient(Config config) {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet();
        this.cellStyles = new CellStyles(config, workbook);
    }

    public void writeToFile(List<SheetEntry> sheetEntries, File file) {
        sheetEntries.forEach(this::writeToSheet);
        createFile(file);
    }

    private void writeToSheet(SheetEntry sheetEntry) {
        sheetEntry
                .getBackground()
                .peek(background -> drawBackground(background, sheetEntry.getSurfaceSize()));
        sheetEntry
                .getCellEntries()
                .forEach(this::draw);
    }

    private void draw(CellEntry cellEntry) {
        int row = cellEntry.getCellAddress().getRow();
        int column = cellEntry.getCellAddress().getColumn();
        CellStyle cellStyle = cellStyles.getCellStyleFor(cellEntry.getBackgroundColor());
        cellStyle = cellStyles.setBordersAround(cellStyle, BorderStyle.THIN);
        String cellValue = cellEntry.getCellValue();
        Cell cell = getRow(row).createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cellValue);
    }

    private <T> void drawBackground(SheetEntry.Background background, SurfaceSize surfaceSize) {
        int startingRow = background.getStartingPoint().getRow();
        int startingColumn = background.getStartingPoint().getColumn();
        int width = surfaceSize.getWidth();
        int height = surfaceSize.getHeight();
        CellStyle cellStyle = cellStyles.getBackgroundCellStyle();
        cellStyles.setBordersAround(cellStyle, BorderStyle.NONE);
        for (int row = startingRow; row < startingRow + height; row++) {
            for (int column = startingColumn; column < startingColumn + width; column++) {
                Cell cell = getRow(row)
                        .createCell(column);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private void createFile(File file) {
        try (OutputStream out = new FileOutputStream(file)) {
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

}
