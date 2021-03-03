package com.noscompany.excel.sheet.entry;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects.ComplexObjectsSheetEntryCreator;
import com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.flat.objects.FlatObjectsSheetEntryCreator;

public class SheetEntryCreator {
    private final FlatObjectsSheetEntryCreator flatObjectsSheetEntryCreator;
    private final ComplexObjectsSheetEntryCreator complexObjectsSheetEntryCreator;

    public SheetEntryCreator(Config config) {
        this.flatObjectsSheetEntryCreator = new FlatObjectsSheetEntryCreator(config);
        this.complexObjectsSheetEntryCreator = new ComplexObjectsSheetEntryCreator(config);
    }

    public SheetEntry fromSingle(Object object, CellAddress startingPosition) {
        return complexObjectsSheetEntryCreator.createFrom(object, startingPosition);
    }

    public SheetEntry fromAll(Iterable<?> objects, CellAddress startingPosition) {
        return flatObjectsSheetEntryCreator.allToOneEntry(objects, startingPosition);
    }
}