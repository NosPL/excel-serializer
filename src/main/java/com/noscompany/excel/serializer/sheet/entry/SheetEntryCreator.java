package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.Size;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElementCreator;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElementList;
import lombok.SneakyThrows;

import java.util.List;

public class SheetEntryCreator {
    private final Config config;
    private final EntryElementCreator entryElementCreator;
    private final CellEntrySizeCalculating cellEntrySizeCalculating;
    private final PositioningEntryElementsOnSheetEntry positioningEntryElementsOnSheetEntry;

    public SheetEntryCreator(Config config) {
        this.config = config;
        this.entryElementCreator = new EntryElementCreator(config);
        this.cellEntrySizeCalculating = new CellEntrySizeCalculating();
        this.positioningEntryElementsOnSheetEntry = new PositioningEntryElementsOnSheetEntry(config);
    }

    @SneakyThrows
    public SheetEntry create(Object object, CellAddress startingPosition) {
        EntryElementList entryElements = entryElementCreator.create(object);
        List<CellEntry> cellEntries = positioningEntryElementsOnSheetEntry.position(entryElements, startingPosition);
        Size size = withPadding(size(cellEntries));
        return new SheetEntry(size, cellEntries, startingPosition);
    }

    private Size withPadding(Size size) {
        int padding = config.getSheetEntryPadding();
        return size.addHeight(padding * 2)
                .addWidth(padding * 2);
    }

    private Size size(List<CellEntry> cellEntries) {
        Size result = cellEntrySizeCalculating.calculate(cellEntries);
        return result;
    }
}