package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import lombok.Value;

@Value
public class Phone {
    @ExcelSimpleField(label = "N. KIERUNKOWY")
    String areaCode;
    @ExcelSimpleField(label = "N. TELEFONU")
    String number;
}
