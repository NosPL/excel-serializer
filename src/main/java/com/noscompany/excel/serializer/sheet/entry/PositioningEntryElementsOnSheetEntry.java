package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.entry.element.EntryElement;
import com.noscompany.excel.serializer.entry.element.EntryElementList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.Config.RecordLayout.HORIZONTAL;
import static com.noscompany.excel.serializer.commons.Config.RecordLayout.VERTICAL;

class PositioningEntryElementsOnSheetEntry {
    private final Config config;
    private final CellEntrySizeCalculating cellEntrySizeCalculating;

    PositioningEntryElementsOnSheetEntry(Config config) {
        this.config = config;
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
    }

    List<CellEntry> position(EntryElementList entryElements, CellAddress startingPosition) {
        startingPosition = paddingOffset(startingPosition);
        List<CellEntry> result = new ArrayList<>();
        EntryElement mainObjectEntry = entryElements.getMainObjectEntry();
        List<EntryElement> singleValueEntryElements = entryElements.getSingleValueEntryElements();
        List<EntryElement> collectionEntryElements = entryElements.getCollectionEntryElements();
        List<CellEntry> cellEntries = drawSingleValueEntryElements(mainObjectEntry, singleValueEntryElements, startingPosition);
        result.addAll(cellEntries);
        int maxWidth = cellEntrySizeCalculating.calculate(cellEntries).getWidth();
        startingPosition = startingPosition.moveToRight(maxWidth + config.getEntryElementOffset());
        result.addAll(drawCollectionEntryElements(collectionEntryElements, startingPosition));
        return result;
    }

    private List<CellEntry> drawSingleValueEntryElements(EntryElement mainObjectEntry, List<EntryElement> entryElements, CellAddress startingPosition) {
        Cursor cursor = Cursor.vertical(startingPosition, config.getSheetEntryOffset());
        List<CellEntry> result = new LinkedList<>();
        List<CellEntry> mainObjectEntries = mainObjectEntry.draw(cursor.position(), HORIZONTAL);
        result.addAll(mainObjectEntries);
        int height = cellEntrySizeCalculating.calculate(mainObjectEntries).getHeight();
        cursor.moveBy(height);
        for (EntryElement e : entryElements) {
            List<CellEntry> cellEntries = e.draw(cursor.position(), HORIZONTAL);
            result.addAll(cellEntries);
            int currentHeight = cellEntrySizeCalculating.calculate(cellEntries).getHeight();
            cursor.moveBy(currentHeight);
        }
        return result;
    }

    private List<CellEntry> drawCollectionEntryElements(List<EntryElement> collectionEntryElements, CellAddress startingPosition) {
        List<CellEntry> result = new LinkedList<>();
        for (EntryElement e : collectionEntryElements) {
            List<CellEntry> cellEntries = e.draw(startingPosition, VERTICAL);
            result.addAll(cellEntries);
            int width = cellEntrySizeCalculating.calculate(cellEntries).getWidth();
            startingPosition = startingPosition.moveToRight(width + config.getEntryElementOffset());
        }
        return result;
    }

    private CellAddress paddingOffset(CellAddress position) {
        return position.move(config.getSheetEntryPadding(), config.getSheetEntryPadding());
    }
}
