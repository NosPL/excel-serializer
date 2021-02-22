package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import io.vavr.control.Option;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.sheet.entry.grid.GridLayout.VERTICAL;

@Value
class Line {
    Option<String> name;
    List<String> values;
    Short nameColor;
    Short valueColor;

    List<CellEntry> draw(CellAddress position, GridLayout gridLayout) {
        Cursor cursor = cursor(position, gridLayout);
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

    private Cursor cursor(CellAddress position, GridLayout gridLayout) {
        if (gridLayout == VERTICAL)
            return Cursor.horizontal(position, 1);
        else
            return Cursor.vertical(position, 1);
    }

    static Line line(String name,
                            List<String> values,
                            Short nameColor,
                            Short valueColor) {
        return new Line(Option.of(name), values, nameColor, valueColor);
    }

    static Line line(List<String> values,
                            Short nameColor,
                            Short valueColor) {
        return new Line(Option.none(), values, nameColor, valueColor);
    }
}