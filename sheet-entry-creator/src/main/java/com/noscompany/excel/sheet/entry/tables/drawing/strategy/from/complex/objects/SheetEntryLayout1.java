package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntries;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.commons.table.Table;
import com.noscompany.excel.commons.table.TablesSequence;

import static com.noscompany.excel.commons.table.Table.Layout.VERTICAL;
import static com.noscompany.excel.commons.table.TablesSequence.DrawingDirection.HORIZONTAL;

class SheetEntryLayout1 extends SheetEntryLayout {

    public SheetEntryLayout1(Config config) {
        super(config);
    }

    @Override
    protected CellAddress offset(SurfaceSize previousTables, CellAddress position) {
        return position.moveToRight(2 + config.getSpacesBetweenTables());
    }

    @Override
    protected CellEntries drawSingleValueTables(TablesSequence tablesSequence, CellAddress startingPosition) {
        return tablesSequence
                .drawingDirection(TablesSequence.DrawingDirection.VERTICAL)
                .layout(Table.Layout.HORIZONTAL)
                .draw(startingPosition);
    }

    @Override
    protected CellEntries drawTablesFromCollections(TablesSequence tablesSequence, CellAddress startingPosition) {
        return tablesSequence
                .drawingDirection(HORIZONTAL)
                .layout(VERTICAL)
                .draw(startingPosition);
    }
}