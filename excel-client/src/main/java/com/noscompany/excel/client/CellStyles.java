package com.noscompany.excel.client;

import com.noscompany.excel.commons.Config;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.WHITE;

class CellStyles {
    private final List<CellStyle> cellStyles;
    private final CellStyle backgroundCellStyle;
    private final CellStyle defaultCellStyle;

    public CellStyles(Config config, Workbook workbook) {
        CellStyle backgroundStyle = create(workbook, config.getSheetEntryBackgroundColor(), BorderStyle.NONE);
        CellStyle labelStyle = create(workbook, config.getTableTitleColor(), BorderStyle.THIN);
        CellStyle fieldNamesCellStyle = create(workbook, config.getRecordLabelsColor(), BorderStyle.THIN);
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
