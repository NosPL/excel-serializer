package com.noscompany.excel.sheet.entry.layout;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.table.Tables;

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
        return draw(complexFieldTables, startingPosition, DrawingDirection.VERTICALLY, Table.Layout.HORIZONTAL);
    }

    @Override
    protected CellAddress offset(SurfaceSize previousTables, CellAddress position) {
        return position.moveToRight(2 + config.getSpacesBetweenTables());
    }

    protected List<CellEntry> drawCollectionFieldsTables(Tables tables, CellAddress startingPosition) {
        List<Table> collectionFieldTables = tables.getCollectionFieldTables();
        return draw(collectionFieldTables, startingPosition, DrawingDirection.HORIZONTALLY, Table.Layout.VERTICAL);
    }
}