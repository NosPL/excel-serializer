package com.noscompany.excel.client;

import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelClient {
    private final Workbook workbook;
    private final org.apache.poi.ss.usermodel.Sheet sheet;
    private final CellStyles cellStyles;

    public ExcelClient(Config config) {
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet();
        this.cellStyles = new CellStyles(config, workbook);
    }

    public void writeToFile(List<SheetEntry> sheetEntries, File file) {
        sheetEntries.forEach(this::writeToSheet);
        createFile(file);
    }

    private void writeToSheet(SheetEntry sheetEntry) {
        paintEntryBackgroundCells(sheetEntry);
        sheetEntry
                .getCellEntries()
                .forEach(this::setCell);
    }

    private void setCell(CellEntry cellEntry) {
        int row = cellEntry.getCellAddress().getRow();
        int column = cellEntry.getCellAddress().getColumn();
        CellStyle cellStyle = cellStyles.getCellStyleFor(cellEntry.getBackgroundColor());
        String cellValue = cellEntry.getCellValue();
        Cell cell = getRow(row).createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cellValue);
    }

    private <T> void paintEntryBackgroundCells(SheetEntry sheetEntry) {
        int startingRow = sheetEntry.getStartingPoint().getRow();
        int startingColumn = sheetEntry.getStartingPoint().getColumn();
        int width = sheetEntry.getSurfaceSize().getWidth();
        int height = sheetEntry.getSurfaceSize().getHeight();
        for (int row = startingRow; row < startingRow + height; row++) {
            for (int column = startingColumn; column < startingColumn + width; column++) {
                Cell cell = getRow(row)
                        .createCell(column);
                cell.setCellStyle(cellStyles.getBackgroundCellStyle());
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
