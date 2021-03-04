package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import lombok.Value;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Value
class IndexedRecord implements Record {
    int index;
    Color color;
    Record record;

    @Override
    public List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout) {
        List<CellEntry> result = new LinkedList<>();
        result.add(index(startingPosition));
        if (tableLayout == Table.Layout.VERTICAL)
            startingPosition = startingPosition.moveToRight(1);
        else
            startingPosition = startingPosition.moveDown(1);
        result.addAll(record.draw(startingPosition, tableLayout));
        return result;
    }

    private CellEntry index(CellAddress startingPosition) {
        return new CellEntry(startingPosition, String.valueOf(index), color);
    }
}
