package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.sheet.entry.SheetEntry;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.WHITE;

class ExcelWriter {
    private final Config config;
    private final Workbook workbook;
    private final Sheet sheet;
    private final CellStyles cellStyles;

    ExcelWriter(Config config) {
        this.config = config;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet();
        this.cellStyles = new CellStyles(config, workbook);
    }

    void writeToSheet(SheetEntry sheetEntry) {
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
        int width = sheetEntry.getSize().getWidth();
        int height = sheetEntry.getSize().getHeight();
        for (int row = startingRow; row < startingRow + height; row++) {
            for (int column = startingColumn; column < startingColumn + width; column++) {
                Cell cell = getRow(row)
                        .createCell(column);
                cell.setCellStyle(cellStyles.getBackgroundCellStyle());
            }
        }
    }

    void saveToFile() {
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

    static class CellStyles {
        private final List<CellStyle> cellStyles;
        private final CellStyle backgroundCellStyle;
        private final CellStyle defaultCellStyle;

        public CellStyles(Config config, Workbook workbook) {
            CellStyle backgroundStyle = create(workbook, config.getEntryBackgroundColor(), BorderStyle.NONE);
            CellStyle labelStyle = create(workbook, config.getLabelColor(), BorderStyle.THIN);
            CellStyle fieldNamesCellStyle = create(workbook, config.getRecordNamesColor(), BorderStyle.THIN);
            CellStyle fieldValuesCellStyle = create(workbook, config.getRecordValuesColor(), BorderStyle.THIN);
            CellStyle defaultCellStyle = create(workbook, WHITE.getIndex(), BorderStyle.THIN);
            cellStyles = List.of(labelStyle, fieldNamesCellStyle, fieldValuesCellStyle);
            this.defaultCellStyle = defaultCellStyle;
            this.backgroundCellStyle = backgroundStyle;
        }

        CellStyle getCellStyleFor(Short color) {
            return cellStyles
                    .stream()
                    .filter(cellStyle -> cellStyle.getFillForegroundColor() == color)
                    .findFirst()
                    .orElse(defaultCellStyle);
        }

        CellStyle getBackgroundCellStyle() {
            return backgroundCellStyle;
        }

        private CellStyle create(Workbook workbook, short color, BorderStyle borderStyle) {
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle = setBordersAround(cellStyle, borderStyle);
            return cellStyle;
        }

        private CellStyle setBordersAround(CellStyle cellStyle, BorderStyle borderStyle) {
            cellStyle.setBorderBottom(borderStyle);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderLeft(borderStyle);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderRight(borderStyle);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderTop(borderStyle);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            return cellStyle;
        }
    }
}
