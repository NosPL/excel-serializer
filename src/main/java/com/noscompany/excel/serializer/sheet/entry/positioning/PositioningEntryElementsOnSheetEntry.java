package com.noscompany.excel.serializer.sheet.entry.positioning;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.sheet.entry.CellEntrySizeCalculating;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElementList;

import java.util.LinkedList;
import java.util.List;

public class PositioningEntryElementsOnSheetEntry {
    private final Config config;
    private final CellEntrySizeCalculating cellEntrySizeCalculating;
    private final PositioningOfNONCollectionField positioningOfNONCollectionField;
    private final PositioningOfCollectionFields positioningOfCollectionFields;

    public PositioningEntryElementsOnSheetEntry(Config config) {
        this.config = config;
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
        this.positioningOfNONCollectionField = new PositioningOfNONCollectionField(config);
        this.positioningOfCollectionFields = new PositioningOfCollectionFields(config);
    }

    public List<CellEntry> position(EntryElementList entryElements, CellAddress position) {
        List<CellEntry> result = new LinkedList<>();
        position = paddingOffset(position);
        List<CellEntry> fromNONCollectionFields = drawNonCollectionFields(entryElements, position);
        result.addAll(fromNONCollectionFields);
        int maxWidth = width(fromNONCollectionFields);
        position = update(position, maxWidth);
        List<CellEntry> fromCollectionFields = drawCollectionFields(entryElements, position);
        result.addAll(fromCollectionFields);
        return result;
    }

    private CellAddress update(CellAddress startingPosition, int maxWidth) {
        return startingPosition.moveToRight(maxWidth + config.getEntryElementOffset());
    }

    private List<CellEntry> drawCollectionFields(EntryElementList entryElements, CellAddress startingPosition) {
        return positioningOfCollectionFields.draw(entryElements, startingPosition);
    }

    private List<CellEntry> drawNonCollectionFields(EntryElementList entryElements, CellAddress startingPosition) {
        return positioningOfNONCollectionField.draw(entryElements, startingPosition);
    }

    private int width(List<CellEntry> cellEntries) {
        return cellEntrySizeCalculating.calculate(cellEntries).getWidth();
    }

    private CellAddress paddingOffset(CellAddress position) {
        return position.move(config.getSheetEntryPadding(), config.getSheetEntryPadding());
    }
}
