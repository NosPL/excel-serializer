package com.noscompany.excel.serializer;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.commons.cursor.Cursor;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;
import io.vavr.collection.Vector;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;

public class ExcelSerializer {
    private final Config config;
    private final ExcelClient excelFileWrite;
    private final SheetEntryCreator sheetEntryCreator;

    public ExcelSerializer(Config config) {
        this.config = config;
        this.excelFileWrite = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    public void serialize(Iterable<?> objects, File file) {
        Cursor cursor = cursor();
        List<SheetEntry> sheetEntries = new LinkedList<>();
        Vector
                .ofAll(objects)
                .filter(Objects::nonNull)
                .forEach(object -> {
                    SheetEntry entry = sheetEntryCreator.createFrom(object, cursor.position());
                    sheetEntries.add(entry);
                    cursor.moveBy(entry.getSurfaceSize());
                });
        excelFileWrite.writeToFile(sheetEntries, file);
    }

    private Cursor cursor() {
        if (config.getSheetLayout() == VERTICAL)
            return Cursor.vertical(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
        else
            return Cursor.horizontal(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
    }
}