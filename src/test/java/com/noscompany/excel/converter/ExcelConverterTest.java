package com.noscompany.excel.converter;

import java.io.File;
import java.util.List;

import static com.noscompany.excel.converter.Config.MainObjectWritingDirection.TOP_TO_BOTTOM;
import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;

public class ExcelConverterTest {

    public static void main(String[] args) {
        List<Employee> people = SampleData.samplePeople(100);
        ExcelGenerator
                .instance(new File("C:\\folder\\People.xls"))
                .simpleFieldsOrientation(VERTICAL)
                .mainObjectWritingDirection(TOP_TO_BOTTOM)
                .collectionLabels(true)
                .objectLabel(true)
                .spaceBetweenEntries(3)
                .spaceBetweenSimpleFieldsAndCollections(2)
                .spaceBetweenCollections(1)
                .startingPosition(2, 2)
                .entryPadding(3)
                .build()
                .save(people);
    }
}