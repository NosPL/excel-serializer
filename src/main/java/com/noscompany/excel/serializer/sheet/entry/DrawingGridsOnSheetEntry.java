package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.sheet.entry.grid.GridLayout;
import com.noscompany.excel.serializer.commons.Size;
import com.noscompany.excel.serializer.commons.cursor.Cursor;
import com.noscompany.excel.serializer.sheet.entry.grid.Grid;
import com.noscompany.excel.serializer.sheet.entry.grid.GridList;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.sheet.entry.grid.GridLayout.VERTICAL;
import static com.noscompany.excel.serializer.sheet.entry.grid.GridLayout.HORIZONTAL;

class DrawingGridsOnSheetEntry {
    private final Config config;
    private final CalculatingSurfaceSizeOfCellEntries calculatingSurfaceSizeOfCellEntries;

    DrawingGridsOnSheetEntry(Config config) {
        this.config = config;
        this.calculatingSurfaceSizeOfCellEntries = new CalculatingSurfaceSizeOfCellEntries();
    }

    List<CellEntry> draw(GridList grids, CellAddress position) {
        List<CellEntry> result = new LinkedList<>();
        List<CellEntry> vertical = drawVertically(grids, position);
        result.addAll(vertical);
        position = position.moveToRight(widthOf(vertical) + entryElementOffset());
        List<CellEntry> horizontal = drawHorizontally(grids, position);
        result.addAll(horizontal);
        return result;
    }

    private int entryElementOffset() {
        return config.getSpacesBetweenGrids();
    }

    private List<CellEntry> drawVertically(GridList gridList, CellAddress startingPosition) {
        Grid mainObjectEntry = gridList.getFromMainObject();
        List<Grid> fromComplexFields = gridList.getFromComplexFields();
        fromComplexFields.add(0, mainObjectEntry);
        return draw(
                fromComplexFields,
                Cursor.vertical(startingPosition, config.getSpacesBetweenGrids()),
                VERTICAL);
    }

    private List<CellEntry> drawHorizontally(GridList entryElements, CellAddress startingPosition) {
        return draw(
                entryElements.getFromCollectionFields(),
                Cursor.horizontal(startingPosition, config.getSpacesBetweenGrids()),
                HORIZONTAL
        );
    }

    private List<CellEntry> draw(List<Grid> fromComplexFields, Cursor cursor, GridLayout gridLayout) {
        List<CellEntry> result = new LinkedList<>();
        for (Grid e : fromComplexFields) {
            List<CellEntry> cellEntries = e.draw(cursor.position(), gridLayout);
            result.addAll(cellEntries);
            Size size = size(cellEntries);
            cursor.moveBy(size);
        }
        return result;
    }

    private Size size(List<CellEntry> cellEntries) {
        return calculatingSurfaceSizeOfCellEntries.calculate(cellEntries);
    }

    private int widthOf(List<CellEntry> cellEntries) {
        return size(cellEntries).getWidth();
    }
}