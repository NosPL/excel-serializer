package com.noscompany.excel.sheet.entry.layout;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.table.Tables;

import java.util.List;

class SheetEntryLayout2 extends SheetEntryLayout {

    public SheetEntryLayout2(Config config) {
        super(config);
    }

    @Override
    protected List<CellEntry> drawMainObjectTableAndComplexFieldsTables(Tables tables, CellAddress startingPosition) {
        Table mainObjectTable = tables.getMainObjectTable();
        List<Table> complexFieldTables = tables.getComplexFieldTables();
        complexFieldTables.add(0, mainObjectTable);
        return draw(complexFieldTables, startingPosition, DrawingDirection.HORIZONTALLY, Table.Layout.VERTICAL);
    }

    @Override
    protected CellAddress offset(SurfaceSize previousTables, CellAddress position) {
        return position.moveDown(3 + config.getSpacesBetweenTables());
    }

    protected List<CellEntry> drawCollectionFieldsTables(Tables tables, CellAddress startingPosition) {
        List<Table> collectionFieldTables = tables.getCollectionFieldTables();
        return draw(collectionFieldTables, startingPosition, DrawingDirection.HORIZONTALLY, Table.Layout.VERTICAL);
    }
}