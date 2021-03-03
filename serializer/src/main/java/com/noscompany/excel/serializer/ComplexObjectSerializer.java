package com.noscompany.excel.serializer;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static io.vavr.collection.Vector.ofAll;

class ComplexObjectSerializer {
    private final Config config;
    private final ExcelClient excelFileWrite;
    private final SheetEntryCreator sheetEntryCreator;

    ComplexObjectSerializer(Config config) {
        this.config = config;
        this.excelFileWrite = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    List<SheetEntry> toSheetEntries(Iterable<?> objects) {
        List<SheetEntry> sheetEntries = new LinkedList<>();
        Cursor cursor = cursor();
        ofAll(objects)
                .filter(Objects::nonNull)
                .forEach(object -> {
                    SheetEntry entry = sheetEntryCreator.fromSingle(object, cursor.position());
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
