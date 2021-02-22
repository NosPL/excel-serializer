package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.Size;
import com.noscompany.excel.serializer.sheet.entry.grid.GridCreator;
import com.noscompany.excel.serializer.sheet.entry.grid.GridList;
import lombok.SneakyThrows;

import java.util.List;

public class SheetEntryCreator {
    private final Config config;
    private final GridCreator gridCreator;
    private final CalculatingSurfaceSizeOfCellEntries calculatingSurfaceSizeOfCellEntries;
    private final DrawingGridsOnSheetEntry drawingGridOnSheet;

    public SheetEntryCreator(Config config) {
        this.config = config;
        this.gridCreator = new GridCreator(config);
        this.calculatingSurfaceSizeOfCellEntries = new CalculatingSurfaceSizeOfCellEntries();
        this.drawingGridOnSheet = new DrawingGridsOnSheetEntry(config);
    }

    @SneakyThrows
    public SheetEntry create(Object object, CellAddress startingPosition) {
        GridList grids = gridCreator.create(object);
        List<CellEntry> cellEntries = drawingGridOnSheet.draw(grids, paddingOffset(startingPosition));
        Size size = withPadding(sizeOf(cellEntries));
        return new SheetEntry(size, cellEntries, startingPosition);
    }

    private CellAddress paddingOffset(CellAddress startingPosition) {
        return startingPosition.moveToRight(config.getSheetEntryPadding()).moveDown(config.getSheetEntryPadding());
    }

    private Size withPadding(Size size) {
        int padding = config.getSheetEntryPadding();
        return size.addHeight(padding * 2)
                .addWidth(padding * 2);
    }

    private Size sizeOf(List<CellEntry> cellEntries) {
        return calculatingSurfaceSizeOfCellEntries.calculate(cellEntries);
    }
}