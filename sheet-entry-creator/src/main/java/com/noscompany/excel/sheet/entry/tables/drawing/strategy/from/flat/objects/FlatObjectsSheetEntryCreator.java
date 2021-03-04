package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.flat.objects;

import com.noscompany.excel.commons.*;
import com.noscompany.excel.commons.SheetEntry.Background;
import com.noscompany.excel.commons.schema.creator.SchemaCreator;
import com.noscompany.excel.commons.table.Table;
import com.noscompany.excel.commons.table.TablesSequence;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.BackgroundCreator;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import static com.noscompany.excel.commons.table.Table.Layout.VERTICAL;
import static com.noscompany.excel.commons.table.TablesSequence.DrawingDirection.HORIZONTAL;

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

    public SheetEntry allToOneEntry(Iterable<? extends JessyObject> objects, CellAddress startingPosition) {
        CellEntries cellEntries = Vector
                .ofAll(objects)
                .map(schemaCreator::create)
                .transform(schemasToTablesMapper::toTables)
                .transform(tables -> draw(tables, startingPosition));
        return toSheetEntry(startingPosition, cellEntries);
    }

    private SheetEntry toSheetEntry(CellAddress startingPosition, CellEntries cellEntries) {
        Option<Background> background = backgroundCreator.createFor(startingPosition);
        return new SheetEntry(cellEntries, background);
    }

    private int padding() {
        return config.getSheetEntryPadding();
    }

    private CellEntries draw(Vector<Table> tables, CellAddress cellAddress) {
        return TablesSequence
                .createFrom(tables)
                .drawingDirection(HORIZONTAL)
                .spacesBetweenTables(spacesBetweenTables())
                .layout(VERTICAL)
                .draw(paddingOffset(cellAddress));
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
