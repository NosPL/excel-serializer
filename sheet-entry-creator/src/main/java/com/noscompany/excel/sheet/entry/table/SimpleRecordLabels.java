package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import lombok.Value;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Value
class SimpleRecordLabels implements RecordLabels {
    List<String> values;
    Color color;

    @Override
    public List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(startingPosition, tableLayout);
        values.forEach(value -> {
            result.add(new CellEntry(cursor.position(), value, color));
            cursor.move();
        });
        return result;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    private Cursor cursor(CellAddress startingPosition, Table.Layout tableLayout) {
        if (tableLayout == Table.Layout.VERTICAL)
            return Cursor.horizontal(startingPosition, 1);
        else
            return Cursor.vertical(startingPosition, 1);
    }
}
