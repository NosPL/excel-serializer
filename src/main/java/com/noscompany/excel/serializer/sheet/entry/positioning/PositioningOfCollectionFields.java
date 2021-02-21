package com.noscompany.excel.serializer.sheet.entry.positioning;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.Size;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.CellEntrySizeCalculating;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElementList;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.Config.RecordLayout.VERTICAL;

class PositioningOfCollectionFields {
    private final Config config;
    private final CellEntrySizeCalculating cellEntrySizeCalculating;

    public PositioningOfCollectionFields(Config config) {
        this.config = config;
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
    }

    List<CellEntry> draw(EntryElementList entryElementList, CellAddress startingPosition) {
        List<CellEntry> result = new LinkedList<>();
        Cursor cursor = cursor(startingPosition);
        entryElementList.getCollectionEntryElements()
                .forEach(e -> {
                    List<CellEntry> cellEntries = e.draw(cursor.position(), VERTICAL);
                    result.addAll(cellEntries);
                    cursor.moveBy(size(cellEntries));
                });
        return result;
    }

    private Size size(List<CellEntry> cellEntries) {
        return cellEntrySizeCalculating.calculate(cellEntries);
    }

    private Cursor cursor(CellAddress startingPosition) {
        return Cursor.horizontal(startingPosition, config.getEntryElementOffset());
    }
}