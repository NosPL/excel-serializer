package com.noscompany.excel.serializer.sample.data;

import com.noscompany.excel.commons.annotations.FieldName;
import lombok.Value;

@Value
public class Phone {
    @FieldName(value = "N. KIERUNKOWY")
    String areaCode;
    @FieldName(value = "N. TELEFONU")
    String number;
}
