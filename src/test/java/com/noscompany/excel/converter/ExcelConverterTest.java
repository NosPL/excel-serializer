package com.noscompany.excel.converter;

import java.io.File;
import java.util.List;

public class ExcelConverterTest {

    public static void main(String[] args) {
        List<Person> people = SampleData.samplePeople(1000);
        File sampleFile = new File("C:\\folder\\People.xls");
        new ExcelGenerator().save(people, sampleFile);
    }

}
