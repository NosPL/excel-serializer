package com.noscompany.excel.serializer;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class ExcelSerializer {
    private final Config config;
    private final ExcelClient excelFileWrite;
    private final SheetEntryCreator sheetEntryCreator;
    private final ComplexObjectSerializer complexObjectSerializer;

    public ExcelSerializer(Config config) {
        this.config = config;
        this.excelFileWrite = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
        this.complexObjectSerializer = new ComplexObjectSerializer(config);
    }

    public void serialize(Collection<?> objects, File file) {
        List<SheetEntry> sheetEntries = toSheetEntries(objects);
        excelFileWrite.writeToFile(sheetEntries, file);
    }

    private List<SheetEntry> toSheetEntries(Iterable<?> objects) {
        if (isFlat(objects)) {
            return complexObjectSerializer.toSheetEntries(objects);
        } else {
            SheetEntry sheetEntry = sheetEntryCreator.fromAll(objects, config.getStartingPosition());
            return List.of(sheetEntry);
        }
    }

    private boolean isFlat(Iterable<?> iterable) {
        if (iterable.iterator().hasNext()) {
            Object next = iterable.iterator().next();
            return hasCollectionFields(next);
        } else
            return false;
    }

    private boolean hasCollectionFields(Object next) {
        return SchemaUtils
                .fieldsFromClass(next.getClass())
                .stream()
                .anyMatch(SchemaUtils::isCollection);
    }
}