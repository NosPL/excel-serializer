package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ClassName;
import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.Value;

@Value
@ClassName(value = "IMIĘ I NAZWISKO")
public class Name {
    @FieldName(Value = "IMIĘ")
    String firstName;
    @FieldName(Value = "NAZWISKO")
    String lastName;
}
