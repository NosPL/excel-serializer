package com.noscompany.excel.serializer.sheet.entry.positioning;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.Size;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.CellEntrySizeCalculating;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElement;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElementList;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.Config.RecordLayout.HORIZONTAL;

class PositioningOfNONCollectionField {
    private final Config config;
    private final CellEntrySizeCalculating cellEntrySizeCalculating;

    PositioningOfNONCollectionField(Config config) {
        this.config = config;
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
    }

    List<CellEntry> draw(EntryElementList entryElementList, CellAddress startingPosition) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(startingPosition);
        entryElements(entryElementList)
                .forEach(e -> {
                    List<CellEntry> entries = e.draw(cursor.position(), HORIZONTAL);
                    result.addAll(entries);
                    cursor.moveBy(size(entries));
                });
        return result;
    }

    private Size size(List<CellEntry> entries) {
        return cellEntrySizeCalculating.calculate(entries);
    }

    private Cursor cursor(CellAddress startingPosition) {
        return Cursor.vertical(startingPosition, config.getSheetEntryOffset());
    }

    private List<EntryElement> entryElements(EntryElementList entryElementList) {
        EntryElement mainObjectEntry = entryElementList.getMainObjectEntry();
        List<EntryElement> singleValueEntryElements = entryElementList.getSingleValueEntryElements();
        singleValueEntryElements.add(0, mainObjectEntry);
        return singleValueEntryElements;
    }
}