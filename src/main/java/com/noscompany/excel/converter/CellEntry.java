package com.noscompany.excel.converter;

import lombok.Value;

@Value
public class CellEntry {
    CellAddress cellAddress;
    String cellValue;

    public CellEntry(int row, int column, String cellValue) {
        this.cellAddress = new CellAddress(row, column);
        this.cellValue = cellValue;
    }

    public CellEntry(CellAddress cellAddress, String cellValue) {
        this.cellAddress = cellAddress;
        this.cellValue = cellValue;
    }
}
