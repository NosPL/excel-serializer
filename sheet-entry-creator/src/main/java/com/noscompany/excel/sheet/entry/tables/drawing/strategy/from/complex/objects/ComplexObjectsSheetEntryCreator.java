package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.*;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaCreator;

import java.util.List;

import static com.noscompany.excel.sheet.entry.CellEntriesUtils.surfaceSizeOf;

public class ComplexObjectsSheetEntryCreator {
    private final Config config;
    private final SchemaCreator schemaCreator;
    private final SchemaToTablesMapper schemaToTablesMapper;

    public ComplexObjectsSheetEntryCreator(Config config) {
        this.config = config;
        this.schemaCreator = new SchemaCreator(config);
        this.schemaToTablesMapper = new SchemaToTablesMapper(config);
    }

    public SheetEntry createFrom(Object object, CellAddress startingPosition) {
        Schema schema = schemaCreator.create(object);
        Tables tables = schemaToTablesMapper.map(schema);
        List<CellEntry> cellEntries = sheetEntryLayout().draw(tables, startingPosition);
        return new SheetEntry(
                withPadding(surfaceSizeOf(cellEntries)),
                cellEntries,
                startingPosition
        );
    }

    private SheetEntryLayout sheetEntryLayout() {
        return SheetEntryLayouts._1.get(config);
    }

    private SurfaceSize withPadding(SurfaceSize surfaceSize) {
        int padding = config.getSheetEntryPadding();
        return surfaceSize.addHeight(padding * 2)
                .addWidth(padding * 2);
    }
}
