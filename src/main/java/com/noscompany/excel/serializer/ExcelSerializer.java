package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.SheetEntry;
import com.noscompany.excel.serializer.sheet.entry.SheetEntryCreator;
import lombok.NonNull;

import java.io.File;
import java.util.Collection;

import static com.noscompany.excel.serializer.commons.Config.SheetEntryLayout.TOP_TO_BOTTOM;

public class ExcelSerializer {
    private final ExcelWriter excelWriter;
    private final Cursor cursor;
    private final SheetEntryCreator sheetEntryCreator;

    public ExcelSerializer(Config config) {
        this.excelWriter = new ExcelWriter(config);
        this.cursor = cursor(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    public <T> void serialize(@NonNull Collection<T> collection) {
        if (collection.isEmpty())
            return;
        for (T object : collection) {
            if (object == null)
                break;
            SheetEntry entry = sheetEntryCreator.create(object, cursor.position());
            excelWriter.writeToSheet(entry);
            cursor.moveBy(entry.getSize());
        }
        excelWriter.saveToFile();
    }

    private Cursor cursor(Config config) {
        if (config.getSheetLayout() == TOP_TO_BOTTOM)
            return Cursor.vertical(config.getStartingPosition(), config.getSheetEntryOffset());
        else
            return Cursor.horizontal(config.getStartingPosition(), config.getSheetEntryOffset());
    }

    public static Config.Builder instance(File file) {
        return Config.builder(file);
    }
}