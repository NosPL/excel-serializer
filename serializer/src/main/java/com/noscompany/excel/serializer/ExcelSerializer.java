package com.noscompany.excel.serializer;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;

import java.io.File;
import java.util.List;

public class ExcelSerializer {
    private final ExcelClient excelFileWrite;
    private final SheetEntryCreator sheetEntryCreator;

    public ExcelSerializer(Config config) {
        this.excelFileWrite = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    public void serialize(Iterable<?> objects, File file) {
        List<SheetEntry> sheetEntries = sheetEntryCreator.createFrom(objects);
        excelFileWrite.writeToFile(sheetEntries, file);
    }
}