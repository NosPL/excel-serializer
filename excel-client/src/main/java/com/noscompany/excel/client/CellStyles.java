package com.noscompany.excel.client;

import com.noscompany.excel.commons.Config;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.util.List;

class CellStyles {
    private final List<XSSFCellStyle> cellStyles;
    private final XSSFCellStyle backgroundCellStyle;
    private final XSSFCellStyle defaultCellStyle;

    public CellStyles(Config config, XSSFWorkbook workbook) {
        XSSFCellStyle backgroundStyle = create(workbook, config.getSheetEntryBackgroundColor(), BorderStyle.NONE);
        XSSFCellStyle labelStyle = create(workbook, config.getTableTitleColor(), BorderStyle.THIN);
        XSSFCellStyle fieldNamesCellStyle = create(workbook, config.getRecordLabelsColor(), BorderStyle.THIN);
        XSSFCellStyle fieldValuesCellStyle = create(workbook, config.getRecordValuesColor(), BorderStyle.THIN);
        XSSFCellStyle indexCellStyle = create(workbook, config.getRecordIndexColor(), BorderStyle.THIN);
        XSSFCellStyle defaultCellStyle = create(workbook, Color.WHITE, BorderStyle.THIN);
        cellStyles = List.of(labelStyle, fieldNamesCellStyle, fieldValuesCellStyle, indexCellStyle);
        this.defaultCellStyle = defaultCellStyle;
        this.backgroundCellStyle = backgroundStyle;
    }

    CellStyle getCellStyleFor(Color color) {
        return cellStyles
                .stream()
                .filter(cellStyle -> cellStyle.getFillForegroundColorColor().equals(new XSSFColor(color, null)))
                .findFirst()
                .orElse(defaultCellStyle);
    }

    CellStyle getBackgroundCellStyle() {
        return backgroundCellStyle;
    }

    private XSSFCellStyle create(XSSFWorkbook workbook, Color color, BorderStyle borderStyle) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(new XSSFColor(color, null));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle = setBordersAround(cellStyle, borderStyle);
        return cellStyle;
    }

    private XSSFCellStyle setBordersAround(XSSFCellStyle cellStyle, BorderStyle borderStyle) {
        return (XSSFCellStyle) setBordersAround((CellStyle) cellStyle, borderStyle);
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
