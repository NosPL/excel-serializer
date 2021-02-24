package com.noscompany.excel.serializer.data;

import com.noscompany.excel.commons.annotations.FieldName;
import lombok.Value;

@Value
public class Name {
    @FieldName(Value = "IMIE")
    String firstName;
    @FieldName(Value = "NAZWISKO")
    String lastName;
}
