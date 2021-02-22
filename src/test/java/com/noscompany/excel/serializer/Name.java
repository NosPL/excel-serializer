package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.Value;

@Value
public class Name {
    @FieldName(Value = "IMIE")
    String firstName;
    @FieldName(Value = "NAZWISKO")
    String lastName;
}
