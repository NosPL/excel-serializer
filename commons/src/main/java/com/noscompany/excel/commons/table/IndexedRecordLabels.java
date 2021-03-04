package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import lombok.Value;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.commons.table.Table.Layout.VERTICAL;

@Value
class IndexedRecordLabels implements RecordLabels {
    RecordLabels recordLabels;

    @Override
    public List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout) {
        if (recordLabels.isEmpty())
            return List.of();
        List<CellEntry> result = new LinkedList<>();
        result.add(0, new CellEntry(startingPosition, "", recordLabels.getColor()));
        if (tableLayout == VERTICAL)
            startingPosition = startingPosition.moveToRight(1);
        else
            startingPosition = startingPosition.moveDown(1);
        result.addAll(recordLabels.draw(startingPosition, tableLayout));
        return result;
    }

    @Override
    public Color getColor() {
        return recordLabels.getColor();
    }

    @Override
    public boolean isEmpty() {
        return recordLabels.isEmpty();
    }
}
