package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntries;
import com.noscompany.excel.commons.cursor.Cursor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.noscompany.excel.commons.table.TablesSequence.DrawingDirection.HORIZONTAL;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class TablesSequence {

    private DrawingDirection drawingDirection = DrawingDirection.VERTICAL;

    private final Iterable<Table> tables;
    private Table.Layout layout = Table.Layout.VERTICAL;
    private int spacesBetweenTables = 1;

    public static TablesSequence createFrom(@NonNull Iterable<Table> tables) {
        return new TablesSequence(tables);
    }

    public CellEntries draw(CellAddress startingPosition) {
        Cursor cursor = getCursor(startingPosition, drawingDirection);
        CellEntries result = CellEntries.empty();
        tables.forEach(table -> {
            CellEntries entries = table.draw(cursor.position(), layout);
            cursor.moveBy(entries.getSurfaceSize());
            result.concat(entries);
        });
        return result;
    }

    private Cursor getCursor(CellAddress startingPosition, DrawingDirection drawingDirection) {
        if (drawingDirection == HORIZONTAL)
            return Cursor.horizontal(startingPosition, spacesBetweenTables);
        else
            return Cursor.vertical(startingPosition, spacesBetweenTables);
    }

    public TablesSequence drawingDirection(DrawingDirection drawingDirection) {
        this.drawingDirection = drawingDirection;
        return this;
    }

    public TablesSequence layout(Table.Layout layout) {
        this.layout = layout;
        return this;
    }

    public TablesSequence spacesBetweenTables(int spacesBetweenTables) {
        this.spacesBetweenTables = spacesBetweenTables;
        return this;
    }

    public enum DrawingDirection {HORIZONTAL, VERTICAL}
}