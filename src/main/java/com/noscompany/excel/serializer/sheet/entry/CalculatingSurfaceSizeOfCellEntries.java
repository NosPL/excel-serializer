package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Size;
import io.vavr.collection.Vector;

import java.util.List;

class CalculatingSurfaceSizeOfCellEntries {

    Size calculate(List<CellEntry> cellEntries) {
        if (cellEntries.isEmpty())
            return new Size(0, 0);
        int biggestColumnIndex = highest(columnIndexFrom(cellEntries));
        int smallestColumnIndex = smallest(columnIndexFrom(cellEntries));
        int biggestRowIndex = highest(rowIndexFrom(cellEntries));
        int smallestRowIndex = smallest(rowIndexFrom(cellEntries));
        int width = biggestColumnIndex - smallestColumnIndex + 1;
        int height = biggestRowIndex - smallestRowIndex + 1;
        return new Size(width, height);
    }

    private <T> T smallest(Vector<T> vector) {
        return vector.sorted().head();
    }

    private <T> T highest(Vector<T> vector) {
        return vector.sorted().last();
    }

    private Vector<Integer> rowIndexFrom(List<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getRow);
    }

    private Vector<Integer> columnIndexFrom(List<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getColumn);
    }

    private Vector<CellAddress> getCellAddresses(List<CellEntry> entryElements) {
        return Vector.ofAll(entryElements).map(CellEntry::getCellAddress);
    }
}