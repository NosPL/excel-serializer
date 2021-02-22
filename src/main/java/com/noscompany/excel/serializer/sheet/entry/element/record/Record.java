package com.noscompany.excel.serializer.sheet.entry.element.record;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config.RecordLayout;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import io.vavr.control.Option;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
public class Record {
    Option<String> name;
    List<String> values;
    Short nameColor;
    Short valueColor;

    public List<CellEntry> draw(CellAddress position, RecordLayout recordLayout) {
        Cursor cursor = Cursor.cursor(position, recordLayout);
        List<CellEntry> result = new LinkedList<>();
        name.peek(name -> {
            result.add(new CellEntry(cursor.position(), name, nameColor));
            cursor.move();
        });
        values.forEach(value -> {
            result.add(new CellEntry(cursor.position(), value, valueColor));
            cursor.move();
        });
        return result;
    }

    public static Record record(String name,
                                List<String> values,
                                Short nameColor,
                                Short valueColor) {
        return new Record(Option.of(name), values, nameColor, valueColor);
    }

    public static Record record(List<String> values,
                                Short nameColor,
                                Short valueColor) {
        return new Record(Option.none(), values, nameColor, valueColor);
    }
}
