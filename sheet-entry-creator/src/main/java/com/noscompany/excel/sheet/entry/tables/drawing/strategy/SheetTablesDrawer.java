package com.noscompany.excel.sheet.entry.tables.drawing.strategy;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import com.noscompany.excel.sheet.entry.CellEntriesUtils;
import com.noscompany.excel.sheet.entry.table.Table;
import io.vavr.collection.Vector;
import lombok.Builder;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.sheet.entry.tables.drawing.strategy.SheetTablesDrawer.DrawingDirection.HORIZONTALLY;
import static io.vavr.collection.Vector.ofAll;

@Builder
public class SheetTablesDrawer {
    private final Iterable<Table> tables;
    private final CellAddress startingPosition;
    private final DrawingDirection drawingDirection;
    private final Table.Layout layout;
    private final int spacesBetweenTables;

    public Vector<CellEntry> draw() {
        Cursor cursor = getCursor(startingPosition, drawingDirection);
        List<CellEntry> result = new LinkedList<>();
        tables.forEach(table -> {
            List<CellEntry> cellEntries = table.draw(cursor.position(), layout);
            cursor.moveBy(CellEntriesUtils.surfaceSizeOf(cellEntries));
            result.addAll(cellEntries);
        });
        return ofAll(result);
    }

    private Cursor getCursor(CellAddress startingPosition, DrawingDirection drawingDirection) {
        if (drawingDirection == HORIZONTALLY)
            return Cursor.horizontal(startingPosition, spacesBetweenTables);
        else
            return Cursor.vertical(startingPosition, spacesBetweenTables);
    }

    public enum DrawingDirection {HORIZONTALLY, VERTICALLY}
}