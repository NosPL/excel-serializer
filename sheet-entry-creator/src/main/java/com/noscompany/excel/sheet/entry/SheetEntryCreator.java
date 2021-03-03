package com.noscompany.excel.sheet.entry;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.JessyObject;
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

    public <T extends JessyObject> SheetEntry createFrom(T object, CellAddress startingPosition) {
        return complexObjectsSheetEntryCreator.createFrom(object, startingPosition);
    }

    public SheetEntry createFrom(Iterable<? extends JessyObject> objects, CellAddress startingPosition) {
        return flatObjectsSheetEntryCreator.allToOneEntry(objects, startingPosition);
    }
}