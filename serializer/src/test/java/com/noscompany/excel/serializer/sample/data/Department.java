package com.noscompany.excel.serializer.sample.data;

import com.noscompany.excel.commons.annotations.FieldName;
import lombok.Value;

@Value
public class Department {
    @FieldName(Value = "NAZWA")
    String name;
    String id;
}
