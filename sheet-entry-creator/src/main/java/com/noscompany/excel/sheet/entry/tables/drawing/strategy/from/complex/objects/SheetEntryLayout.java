package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntries;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.commons.table.TablesSequence;

abstract class SheetEntryLayout {
    protected final Config config;

    SheetEntryLayout(Config config) {
        this.config = config;
    }

    CellEntries draw(Tables tables, CellAddress startingPosition) {
        startingPosition = paddingOffset(startingPosition);
        CellEntries cellEntries = drawSingleValueTables(tables.getSingleValueTables(), startingPosition);
        startingPosition = offset(cellEntries.getSurfaceSize(), startingPosition);
        cellEntries.concat(drawTablesFromCollections(tables.getTablesFromCollection(), startingPosition));
        return cellEntries;
    }

    private CellAddress paddingOffset(CellAddress startingPosition) {
        return startingPosition.moveDown(config.getSheetEntryPadding()).moveToRight(config.getSheetEntryPadding());
    }

    protected abstract CellAddress offset(SurfaceSize previousTables, CellAddress position);

    protected abstract CellEntries drawSingleValueTables(TablesSequence tablesSequence, CellAddress startingPosition);

    protected abstract CellEntries drawTablesFromCollections(TablesSequence tablesSequence, CellAddress startingPosition);
}