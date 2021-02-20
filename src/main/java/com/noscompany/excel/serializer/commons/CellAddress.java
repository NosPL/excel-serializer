package com.noscompany.excel.serializer.commons;

import lombok.Value;

@Value
public class CellAddress {
    int row;
    int column;

    public CellAddress(int row, int column) {
        if (row < 0)
            throw new IllegalArgumentException("Row cannot be < 0");
        if (column < 0)
            throw new IllegalArgumentException("Column cannot be < 0");
        this.row = row;
        this.column = column;
    }

    public CellAddress moveToRight(int offset) {
        return new CellAddress(row, column + offset);
    }

    public CellAddress moveDown(int offset) {
        return new CellAddress(row + offset, column);
    }

    public CellAddress move(int rowOffset, int columnOffset) {
        return new CellAddress(row + rowOffset, column + columnOffset);
    }
}
