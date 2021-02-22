package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.sheet.entry.grid.GridLayout.VERTICAL;
import static java.util.Arrays.asList;

@Value
class Lines {
    List<Line> lines;

    List<CellEntry> draw(CellAddress position, GridLayout gridLayout) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(position, gridLayout);
        for (Line line : lines) {
            result.addAll(line.draw(cursor.position(), gridLayout));
            cursor.move();
        }
        return result;
    }

    private Cursor cursor(CellAddress position, GridLayout gridLayout) {
        if (gridLayout == VERTICAL)
            return Cursor.vertical(position, 1);
        else
            return Cursor.horizontal(position, 1);
    }

    static Lines of(Line...lines) {
        return new Lines(asList(lines));
    }
}
