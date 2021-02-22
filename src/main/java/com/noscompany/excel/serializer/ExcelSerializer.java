package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.SheetEntry;
import com.noscompany.excel.serializer.sheet.entry.SheetEntryCreator;
import lombok.NonNull;

import java.io.File;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.Config.SheetEntryLayout.TOP_TO_BOTTOM;
import static io.vavr.collection.Vector.of;

public class ExcelSerializer {
    private final Config config;
    private final ExcelWriter excelWriter;
    private final SheetEntryCreator sheetEntryCreator;

    public ExcelSerializer(Config config) {
        this.config = config;
        this.excelWriter = new ExcelWriter(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    public void serialize(@NonNull Object... objects) {
        Cursor cursor = cursor(config);
        of(objects)
                .filter(Objects::nonNull)
                .forEach(object -> {
                    SheetEntry entry = sheetEntryCreator.create(object, cursor.position());
                    cursor.moveBy(entry.getSize());
                    excelWriter.writeToSheet(entry);
                });
        excelWriter.saveToFile();
    }

    private Cursor cursor(Config config) {
        if (config.getSheetLayout() == TOP_TO_BOTTOM)
            return Cursor.vertical(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
        else
            return Cursor.horizontal(config.getStartingPosition(), config.getSpacesBetweenSheetEntries());
    }

    public static Config.Builder builder(File file) {
        return Config.builder(file);
    }

    public static ExcelSerializer instance(File file) {
        return new ExcelSerializer(Config.defaultConfig(file));
    }
}