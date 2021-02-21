package com.noscompany.excel.serializer;

import java.io.File;
import java.util.List;

import static com.noscompany.excel.serializer.commons.Config.SheetEntryLayout.LEFT_TO_RIGHT;
import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.*;

public class ExcelConverterTest {

    public static void main(String[] args) {
        List<Employee> people = SampleData.samplePeople(100);
        ExcelSerializer
                .instance(new File("C:\\folder\\Employees.xls"))
                .labels(true)
                .startingPosition(1, 1)
                .sheetLayout(LEFT_TO_RIGHT)
                .sheetEntryOffset(1)
                .sheetEntryPadding(1)
                .entryElementOffset(1)
                .labelColor(LIME.getIndex())
                .recordNamesColor(LIGHT_ORANGE.getIndex())
                .recordValuesColor(LIGHT_YELLOW.getIndex())
                .entryBackgroundColor(AQUA.getIndex())
                .build()
                .serialize(people);
    }
}