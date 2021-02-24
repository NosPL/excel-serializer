package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.sheet.entry.cursor.Cursor;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import java.util.LinkedList;
import java.util.List;

class Records {
    private Option<Record> labels = Option.none();
    private final List<Record> records = new LinkedList<>();

    void addLabels(List<String> labels, short color) {
        if (labels.isEmpty())
            return;
        this.labels = Option.of(new Record(labels, color));
    }

    void addRecords(short color, List<List<String>> records) {
        if (records.isEmpty())
            return;
        this.records.addAll(
                Vector.ofAll(records)
                        .map(record -> new Record(record, color))
                        .toJavaList()
        );
    }

    List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(startingPosition, tableLayout);
        if (labels.isDefined()) {
            Record record = labels.get();
            result.addAll(record.draw(cursor.position(), tableLayout));
            cursor.move();
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

    static Records empty() {
        return new Records();
    }
}
