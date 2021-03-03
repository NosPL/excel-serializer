package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import com.noscompany.excel.serializer.SerializationStrategy;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static com.noscompany.excel.serializer.strategies.Utils.isFlat;
import static io.vavr.collection.Vector.ofAll;

class ComplexObjectsStrategy implements SerializationStrategy {
    private final Config config;
    private final ExcelClient excelFileWrite;
    private final SheetEntryCreator sheetEntryCreator;

    ComplexObjectsStrategy(Config config) {
        this.config = config;
        this.excelFileWrite = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    @Override
    public void serialize(Iterable<? extends JessyObject> objects, File file) {
        List<SheetEntry> sheetEntries = toSheetEntries(objects);
        excelFileWrite.writeToFile(sheetEntries, file);
    }

    @Override
    public boolean accepts(Iterable<? extends JessyObject> objects) {
        return !isFlat(objects);
    }

    List<SheetEntry> toSheetEntries(Iterable<? extends JessyObject> objects) {
        List<SheetEntry> sheetEntries = new LinkedList<>();
        Cursor cursor = cursor();
        ofAll(objects)
                .filter(Objects::nonNull)
                .forEach(object -> {
                    SheetEntry entry = sheetEntryCreator.createFrom(object, cursor.position());
                    sheetEntries.add(entry);
                    cursor.moveBy(entry.getSurfaceSize());
                });
        return sheetEntries;
    }

    private Cursor cursor() {
        if (config.getSheetLayout() == VERTICAL)
            return Cursor.vertical(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
        else
            return Cursor.horizontal(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
    }
}
