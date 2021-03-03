package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.commons.cursor.Cursor;
import com.noscompany.excel.sheet.entry.CellEntriesUtils;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.SheetTablesDrawer;

import java.util.LinkedList;
import java.util.List;

abstract class SheetEntryLayout {
    protected final Config config;

    SheetEntryLayout(Config config) {
        this.config = config;
    }

    List<CellEntry> draw(Tables tables, CellAddress startingPosition) {
        startingPosition = paddingOffset(startingPosition);
        List<CellEntry> result = new LinkedList<>();
        List<CellEntry> complexFields = drawMainObjectTableAndComplexFieldsTables(tables, startingPosition);
        result.addAll(complexFields);
        startingPosition = offset(CellEntriesUtils.surfaceSizeOf(complexFields), startingPosition);
        result.addAll(drawCollectionFieldsTables(tables, startingPosition));
        return result;
    }

    private CellAddress paddingOffset(CellAddress startingPosition) {
        return startingPosition.moveDown(config.getSheetEntryPadding()).moveToRight(config.getSheetEntryPadding());
    }

    protected abstract CellAddress offset(SurfaceSize previousTables, CellAddress position);

    protected abstract List<CellEntry> drawMainObjectTableAndComplexFieldsTables(Tables tables, CellAddress startingPosition);

    protected abstract List<CellEntry> drawCollectionFieldsTables(Tables tables, CellAddress startingPosition);

    protected List<CellEntry> draw(List<Table> tables, CellAddress startingPosition, SheetTablesDrawer.DrawingDirection drawingDirection, Table.Layout tableLayout) {
        Cursor cursor = getCursor(startingPosition, drawingDirection);
        List<CellEntry> result = new LinkedList<>();
        tables.forEach(table -> {
            List<CellEntry> cellEntries = table.draw(cursor.position(), tableLayout);
            cursor.moveBy(CellEntriesUtils.surfaceSizeOf(cellEntries));
            result.addAll(cellEntries);
        });
        return result;
    }

    private Cursor getCursor(CellAddress startingPosition, SheetTablesDrawer.DrawingDirection drawingDirection) {
        if (drawingDirection == SheetTablesDrawer.DrawingDirection.HORIZONTALLY)
            return Cursor.horizontal(startingPosition, config.getSpacesBetweenTables());
        else
            return Cursor.vertical(startingPosition, config.getSpacesBetweenTables());
    }
}