package com.noscompany.excel.commons;

import lombok.Value;

@Value
public class CellEntry {
    CellAddress cellAddress;
    String cellValue;
    Short backgroundColor;
}
