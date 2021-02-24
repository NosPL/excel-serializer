package com.noscompany.excel.serializer.data;

import com.noscompany.excel.commons.annotations.FieldName;
import lombok.Value;

@Value
public class Phone {
    @FieldName(Value = "N. KIERUNKOWY")
    String areaCode;
    @FieldName(Value = "N. TELEFONU")
    String number;
}
