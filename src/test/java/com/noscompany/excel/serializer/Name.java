package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ExcelObject;
import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import lombok.Value;

@Value
@ExcelObject(label = "IMIĘ I NAZWISKO")
public class Name {
    @ExcelSimpleField(label = "IMIĘ")
    String firstName;
    @ExcelSimpleField(label = "NAZWISKO")
    String lastName;
}
