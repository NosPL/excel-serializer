package com.noscompany.excel.converter;

import com.noscompany.excel.converter.entries.CellEntryCreator;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class ExcelGenerator {
    private final CellEntryCreator cellEntriesCreator;
    private final ExcelSerializer excelSerializer;
    private final Cursor cursor;

    ExcelGenerator(Config config) {
        this.cellEntriesCreator = new CellEntryCreator(config);
        this.excelSerializer = new ExcelSerializer(config);
        this.cursor = new Cursor(config);
    }

    public <T> void save(Collection<T> collection) {
        if (collection.isEmpty())
            return;
        for (T object : collection) {
            Set<CellEntry> cellEntries = cellEntriesCreator.create(object, cursor.getCurrentPosition());
            excelSerializer.serialize(cellEntries);
            cursor.moveToNextStartingPoint(object);
        }
        excelSerializer.saveToFile();
    }

    public static Config.Builder instance(File file) {
        return Config.builder(file);
    }
}