package com.noscompany.excel.sheet.entry.utils;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.SheetEntry;
import com.noscompany.excel.commons.SurfaceSize;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SheetEntryAssertions {
    private final SheetEntry sheetEntry;
    private final List<CellEntry> cellEntries;

    private SheetEntryAssertions(SheetEntry sheetEntry) {
        this.sheetEntry = sheetEntry;
        this.cellEntries = new ArrayList<>(sheetEntry.getCellEntries());
    }

    public static SheetEntryAssertions assertThat(SheetEntry sheetEntry) {
        return new SheetEntryAssertions(sheetEntry);
    }

    public SheetEntryAssertions andNothingMore() {
        assertTrue(cellEntries.isEmpty(), "Some cell entries left after assertions: " + cellEntries);
        return this;
    }

    public SheetEntryAssertions surfaceSizeEquals(SurfaceSize surfaceSize) {
        assertEquals(surfaceSize, sheetEntry.getSurfaceSize());
        return this;
    }

    public AtPosition at(CellAddress position) {
        return new AtPosition(position, this);
    }

    public static class AtPosition {
        private final CellAddress cellAddress;
        private final SheetEntryAssertions sheetEntryAssertions;

        private AtPosition(CellAddress cellAddress, SheetEntryAssertions sheetEntryAssertions) {
            this.cellAddress = cellAddress;
            this.sheetEntryAssertions = sheetEntryAssertions;
        }

        //        @SafeVarargs
        public final SheetEntryAssertions containsTable(Record... records) {
            new TableAssertion(cellAddress, asList(records)).assertContains(sheetEntryAssertions.cellEntries);
            return sheetEntryAssertions;
        }
    }
}

