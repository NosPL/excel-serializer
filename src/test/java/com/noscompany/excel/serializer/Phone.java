package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.Value;

@Value
public class Phone {
    @FieldName(Value = "N. KIERUNKOWY")
    String areaCode;
    @FieldName(Value = "N. TELEFONU")
    String number;
}
