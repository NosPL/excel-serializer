package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.serializer.sample.data.Employee;

import java.io.File;
import java.util.List;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static com.noscompany.excel.serializer.sample.data.SampleData.randomEmployees;
import static java.awt.Color.*;

public class ManualTest {

    public static void main(String[] args) {
        File file = new File("C:\\folder\\Employees.xlsx");
        List<Employee> employees = randomEmployees(1000);
        Config config = Config
                .builder()
                .startingPosition(4, 1)
                .sheetLayout(VERTICAL)
                .spacesBetweenSheetEntries(3)
                .setBackground(1, CYAN)
                .spacesBetweenTables(1)
                .tablesTitleColor(GREEN)
                .recordLabelsColor(ORANGE)
                .recordIndexColor(PINK)
                .recordsColor(WHITE)
                .allowNestedCollections(true)
                .flattenNestedCollections(true)
                .indexedTableRecords(true)
                .build();
        new ExcelSerializer(config).serialize(employees, file);
    }
}