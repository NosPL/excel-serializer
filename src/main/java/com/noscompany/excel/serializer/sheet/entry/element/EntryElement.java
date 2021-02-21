package com.noscompany.excel.serializer.sheet.entry.element;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config.RecordLayout;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.element.record.Record;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.Config.RecordLayout.HORIZONTAL;

@AllArgsConstructor
@Getter
public class EntryElement {
    private final Option<Label> label;
    private final List<Record> records;


    public List<CellEntry> draw(CellAddress position, RecordLayout recordLayout) {
        List<CellEntry> result = new LinkedList<>();
        if (label.isDefined()) {
            result.add(new CellEntry(position, label.get().getValue(), label.get().getColor()));
            position = position.moveDown(1);
        }
        Cursor cursor = cursor(position, recordLayout);
        for (Record record : records) {
            result.addAll(record.draw(cursor.position(), recordLayout));
            cursor.move();
        }
        return result;
    }

    private Cursor cursor(CellAddress position, RecordLayout recordLayout) {
        if (recordLayout == HORIZONTAL)
            return Cursor.vertical(position, 1);
        else
            return Cursor.horizontal(position, 1);
    }

    @Value
    public static class Label {
        String value;
        Short color;
    }
}