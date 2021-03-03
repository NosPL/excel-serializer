package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.SheetTablesDrawer;

import java.util.List;

class SheetEntryLayout1 extends SheetEntryLayout {

    public SheetEntryLayout1(Config config) {
        super(config);
    }

    @Override
    protected List<CellEntry> drawMainObjectTableAndComplexFieldsTables(Tables tables, CellAddress startingPosition) {
        Table mainObjectTable = tables.getMainObjectTable();
        List<Table> complexFieldTables = tables.getComplexFieldTables();
        complexFieldTables.add(0, mainObjectTable);
        return draw(complexFieldTables, startingPosition, SheetTablesDrawer.DrawingDirection.VERTICALLY, Table.Layout.HORIZONTAL);
    }

    @Override
    protected CellAddress offset(SurfaceSize previousTables, CellAddress position) {
        return position.moveToRight(2 + config.getSpacesBetweenTables());
    }

    protected List<CellEntry> drawCollectionFieldsTables(Tables tables, CellAddress startingPosition) {
        List<Table> collectionFieldTables = tables.getCollectionFieldTables();
        return draw(collectionFieldTables, startingPosition, SheetTablesDrawer.DrawingDirection.HORIZONTALLY, Table.Layout.VERTICAL);
    }
}