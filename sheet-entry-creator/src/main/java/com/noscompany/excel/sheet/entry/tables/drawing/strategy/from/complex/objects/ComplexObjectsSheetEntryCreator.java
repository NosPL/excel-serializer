package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.*;
import com.noscompany.excel.commons.SheetEntry.Background;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaCreator;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.BackgroundCreator;
import io.vavr.control.Option;

import java.util.List;

import static com.noscompany.excel.sheet.entry.CellEntriesUtils.surfaceSizeOf;

public class ComplexObjectsSheetEntryCreator {
    private final Config config;
    private final SchemaCreator schemaCreator;
    private final SchemaToTablesMapper schemaToTablesMapper;
    private final BackgroundCreator backgroundCreator;

    public ComplexObjectsSheetEntryCreator(Config config) {
        this.config = config;
        this.schemaCreator = new SchemaCreator(config);
        this.schemaToTablesMapper = new SchemaToTablesMapper(config);
        this.backgroundCreator = new BackgroundCreator(config);
    }

    public SheetEntry createFrom(JessyObject object, CellAddress startingPosition) {
        Schema schema = schemaCreator.create(object);
        Tables tables = schemaToTablesMapper.map(schema);
        List<CellEntry> cellEntries = sheetEntryLayout().draw(tables, startingPosition);
        Option<Background> background = backgroundCreator.createFor(startingPosition);
        return new SheetEntry(surfaceSizeOf(cellEntries), cellEntries, background);
    }

    private SheetEntryLayout sheetEntryLayout() {
        return SheetEntryLayouts._1.get(config);
    }
}
