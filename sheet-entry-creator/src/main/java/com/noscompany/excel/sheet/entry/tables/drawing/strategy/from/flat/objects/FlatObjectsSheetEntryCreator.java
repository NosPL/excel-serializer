package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.flat.objects;

import com.noscompany.excel.commons.*;
import com.noscompany.excel.commons.SheetEntry.Background;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaCreator;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.BackgroundCreator;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.SheetTablesDrawer;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import static com.noscompany.excel.sheet.entry.CellEntriesUtils.surfaceSizeOf;
import static com.noscompany.excel.sheet.entry.table.Table.Layout.VERTICAL;
import static com.noscompany.excel.sheet.entry.tables.drawing.strategy.SheetTablesDrawer.DrawingDirection.HORIZONTALLY;

public class FlatObjectsSheetEntryCreator {
    private Config config;
    private SchemaCreator schemaCreator;
    private SchemasToTablesMapper schemasToTablesMapper;
    private BackgroundCreator backgroundCreator;

    public FlatObjectsSheetEntryCreator(Config config) {
        this.config = config;
        this.schemaCreator = new SchemaCreator(config);
        this.schemasToTablesMapper = new SchemasToTablesMapper(config);
        this.backgroundCreator = new BackgroundCreator(config);
    }

    public SheetEntry allToOneEntry(Iterable<?> objects, CellAddress startingPosition) {
        return Vector
                .ofAll(objects)
                .map(schemaCreator::create)
                .transform(schemasToTablesMapper::toTables)
                .transform(tables -> draw(tables, startingPosition))
                .transform(cellEntries -> toSheetEntry(startingPosition, cellEntries));
    }

    private SheetEntry toSheetEntry(CellAddress startingPosition, Vector<CellEntry> cellEntries) {
        Option<Background> background = backgroundCreator.createFor(startingPosition);
        SurfaceSize surfaceSize = surfaceSizeOf(cellEntries);
        return new SheetEntry(
                surfaceSize,
                cellEntries.toJavaList(),
                background);
    }

    private int padding() {
        return config.getSheetEntryPadding();
    }

    private Vector<CellEntry> draw(Vector<Table> tables, CellAddress cellAddress) {
        return SheetTablesDrawer
                .builder()
                .tables(tables)
                .drawingDirection(HORIZONTALLY)
                .spacesBetweenTables(spacesBetweenTables())
                .layout(VERTICAL)
                .startingPosition(paddingOffset(cellAddress))
                .build()
                .draw();
    }

    private int spacesBetweenTables() {
        return config.getSpacesBetweenTables();
    }

    private CellAddress paddingOffset(CellAddress cellAddress) {
        return cellAddress
                .moveToRight(padding())
                .moveDown(padding());
    }
}
