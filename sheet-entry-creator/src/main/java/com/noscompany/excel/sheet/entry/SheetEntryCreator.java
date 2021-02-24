package com.noscompany.excel.sheet.entry;

import com.noscompany.excel.commons.*;
import com.noscompany.excel.sheet.entry.cursor.Cursor;
import com.noscompany.excel.sheet.entry.layout.SheetEntryLayout;
import com.noscompany.excel.sheet.entry.layout.SheetEntryLayouts;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaCreator;
import com.noscompany.excel.sheet.entry.table.Tables;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static com.noscompany.excel.sheet.entry.CellEntriesUtils.surfaceSizeOf;
import static io.vavr.collection.Vector.ofAll;

public class SheetEntryCreator {
    private final Config config;
    private final SchemaCreator schemaCreator;
    private final SchemaToTablesMapper schemaToTablesMapper;

    public SheetEntryCreator(Config config) {
        this.config = config;
        this.schemaCreator = new SchemaCreator(config);
        this.schemaToTablesMapper = new SchemaToTablesMapper(config);
    }

    public List<SheetEntry> createFrom(Iterable<?> objects) {
        Cursor cursor = cursor(config);
        List<SheetEntry> sheetEntries = new LinkedList<>();
        ofAll(objects)
                .filter(Objects::nonNull)
                .forEach(object -> {
                    Schema schema = schemaCreator.create(object);
                    Tables tables = schemaToTablesMapper.map(schema);
                    List<CellEntry> cellEntries = tableDrawer().draw(tables, cursor.position());
                    SheetEntry entry = create(cellEntries, cursor.position());
                    sheetEntries.add(entry);
                    cursor.moveBy(entry.getSurfaceSize());
                });
        return sheetEntries;
    }

    private Cursor cursor(Config config) {
        if (config.getSheetLayout() == VERTICAL)
            return Cursor.vertical(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
        else
            return Cursor.horizontal(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
    }

    private SheetEntryLayout tableDrawer() {
        return SheetEntryLayouts._1.get(config);
    }

    public SheetEntry create(List<CellEntry> cellEntries, CellAddress startingPosition) {
        return new SheetEntry(
                withPadding(surfaceSizeOf(cellEntries)),
                cellEntries,
                startingPosition
        );
    }

    private SurfaceSize withPadding(SurfaceSize surfaceSize) {
        int padding = config.getSheetEntryPadding();
        return surfaceSize.addHeight(padding * 2)
                .addWidth(padding * 2);
    }
}