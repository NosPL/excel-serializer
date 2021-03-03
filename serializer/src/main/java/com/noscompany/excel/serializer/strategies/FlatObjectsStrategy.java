package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.client.ExcelClient;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.serializer.SerializationStrategy;
import com.noscompany.excel.sheet.entry.SheetEntryCreator;

import java.io.File;
import java.util.List;

import static com.noscompany.excel.serializer.strategies.Utils.isFlat;

class FlatObjectsStrategy implements SerializationStrategy {
    private final Config config;
    private final ExcelClient excelClient;
    private final SheetEntryCreator sheetEntryCreator;

    public FlatObjectsStrategy(Config config) {
        this.config = config;
        this.excelClient = new ExcelClient(config);
        this.sheetEntryCreator = new SheetEntryCreator(config);
    }

    @Override
    public void serialize(Iterable<?> objects, File file) {
        SheetEntry sheetEntry = sheetEntryCreator.fromAll(objects, config.getStartingPosition());
        excelClient.writeToFile(List.of(sheetEntry), file);
    }

    @Override
    public boolean accepts(Iterable<?> objects) {
        return isFlat(objects);
    }
}
