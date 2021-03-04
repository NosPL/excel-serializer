package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import lombok.Value;

import java.awt.*;

@Value
class Title {
    String value;
    Color color;

    CellEntry toCellEntry(CellAddress cellAddress) {
        return new CellEntry(cellAddress, value, color);
    }
}