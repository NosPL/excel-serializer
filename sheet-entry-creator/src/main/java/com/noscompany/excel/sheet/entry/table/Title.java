package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import lombok.Value;

@Value
class Title {
    String value;
    short color;

    CellEntry toCellEntry(CellAddress cellAddress) {
        return new CellEntry(cellAddress, value, color);
    }
}