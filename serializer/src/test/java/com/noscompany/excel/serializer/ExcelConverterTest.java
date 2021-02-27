package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.serializer.data.Employee;

import java.io.File;
import java.util.List;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static com.noscompany.excel.serializer.data.SampleData.randomEmployees;
import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.*;

public class ExcelConverterTest {

    public static void main(String[] args) {
        File file = new File("C:\\folder\\Employees.xls");
        List<Employee> employees = randomEmployees(100);
        Config config = Config.builder()
                .startingPosition(4, 1)
                .sheetLayout(VERTICAL)
                .spacesBetweenSheetEntries(3)
                .sheetEntryPadding(1)
                .spacesBetweenTables(1)
                .tablesTitleColor(LIME.getIndex())
                .recordLabelsColor(LIGHT_ORANGE.getIndex())
                .recordsColor(LIGHT_YELLOW.getIndex())
                .sheetEntryBackgroundColor(AQUA.getIndex())
                .allowNestedCollections(true)
                .indexedTableRecords(true)
                .build();
        new ExcelSerializer(config)
                .serialize(employees, file);
    }
}