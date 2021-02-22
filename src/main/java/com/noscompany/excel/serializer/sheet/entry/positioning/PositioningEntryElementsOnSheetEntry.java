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
    private final PositioningOfNONCollectionField drawingNONCollectionField;
    private final PositioningOfCollectionFields drawingCollectionFields;

    public PositioningEntryElementsOnSheetEntry(Config config) {
        this.config = config;
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
        this.drawingNONCollectionField = new PositioningOfNONCollectionField(config);
        this.drawingCollectionFields = new PositioningOfCollectionFields(config);
    }

    public List<CellEntry> draw(EntryElementList entryElements, CellAddress position) {
        List<CellEntry> result = new LinkedList<>();
        position = paddingOffset(position);
        List<CellEntry> fromNONCollectionFields = drawNonCollectionFields(entryElements, position);
        result.addAll(fromNONCollectionFields);
        position = position.moveToRight(widthOf(fromNONCollectionFields) + EntryElementOffset());
        List<CellEntry> fromCollectionFields = drawCollectionFields(entryElements, position);
        result.addAll(fromCollectionFields);
        return result;
    }

    private int EntryElementOffset() {
        return config.getEntryElementOffset();
    }

    private List<CellEntry> drawCollectionFields(EntryElementList entryElements, CellAddress startingPosition) {
        return drawingCollectionFields.draw(entryElements, startingPosition);
    }

    private List<CellEntry> drawNonCollectionFields(EntryElementList entryElements, CellAddress startingPosition) {
        return drawingNONCollectionField.draw(entryElements, startingPosition);
    }

    private int widthOf(List<CellEntry> cellEntries) {
        return cellEntrySizeCalculating.calculate(cellEntries).getWidth();
    }

    private CellAddress paddingOffset(CellAddress position) {
        return position
                .moveToRight(config.getSheetEntryPadding())
                .moveDown(config.getSheetEntryPadding());
    }
}
