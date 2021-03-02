package com.noscompany.excel.commons;

import lombok.Value;

import java.awt.*;

@Value
public class CellEntry {
    CellAddress cellAddress;
    String cellValue;
    Color backgroundColor;
}
