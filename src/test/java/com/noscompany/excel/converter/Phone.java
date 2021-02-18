package com.noscompany.excel.converter;

import com.noscompany.excel.converter.annotations.ExcelField;
import lombok.Value;

@Value
public class Phone {
    @ExcelField("N. KIERUNKOWY")
    String areaCode;
    @ExcelField("N. TELEFONU")
    String number;
}
