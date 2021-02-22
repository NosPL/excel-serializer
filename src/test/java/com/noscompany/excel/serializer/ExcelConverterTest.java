package com.noscompany.excel.serializer;

import java.io.File;

import static com.noscompany.excel.serializer.commons.Config.SheetEntryLayout.TOP_TO_BOTTOM;
import static com.noscompany.excel.serializer.sample.data.SampleData.randomEmployees;
import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.*;

public class ExcelConverterTest {

    public static void main(String[] args) {
        ExcelSerializer
                .builder(new File("C:\\folder\\Employees.xls"))
                .labels(true)
                .startingPosition(4, 1)
                .sheetLayout(TOP_TO_BOTTOM)
                .spacesBetweenSheetEntries(3)
                .sheetEntryPadding(1)
                .spacesBetweenGrids(1)
                .labelColor(LIME.getIndex())
                .recordNamesColor(LIGHT_ORANGE.getIndex())
                .recordValuesColor(LIGHT_YELLOW.getIndex())
                .entryBackgroundColor(AQUA.getIndex())
                .build()
                .serialize(randomEmployees(100));
    }
}