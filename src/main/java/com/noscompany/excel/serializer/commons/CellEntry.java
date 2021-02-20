package com.noscompany.excel.serializer.commons;

import lombok.Value;
import org.apache.poi.hssf.util.HSSFColor;

@Value
public class CellEntry {
    CellAddress cellAddress;
    String cellValue;
    Short backgroundColor;
}
