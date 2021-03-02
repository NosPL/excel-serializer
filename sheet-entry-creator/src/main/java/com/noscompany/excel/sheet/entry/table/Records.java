package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import io.vavr.control.Option;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
class Records {
    Option<RecordLabels> labels;
    List<Record> records;

    List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(startingPosition, tableLayout);
        if (labels.isDefined()) {
            if (!labels.get().isEmpty()) {
                RecordLabels recordLabels = labels.get();
                result.addAll(recordLabels.draw(cursor.position(), tableLayout));
                cursor.move();
            }
        }
        records.forEach(record -> {
            result.addAll(record.draw(cursor.position(), tableLayout));
            cursor.move();
        });
        return result;
    }

    private Cursor cursor(CellAddress startingPosition, Table.Layout tableLayout) {
        if (tableLayout == Table.Layout.VERTICAL)
            return Cursor.vertical(startingPosition, 1);
        else
            return Cursor.horizontal(startingPosition, 1);
    }
}