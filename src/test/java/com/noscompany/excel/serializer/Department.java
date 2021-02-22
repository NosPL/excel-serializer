package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.Value;

@Value
public class Department {
    @FieldName(Value = "NAZWA")
    String name;
    String id;
}
