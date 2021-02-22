package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Size;
import io.vavr.collection.Vector;

import java.util.List;

public class CellEntrySizeCalculating {

    public Size calculate(List<CellEntry> cellEntries) {
        int latestColumn = getColumns(cellEntries).sorted().last();
        int earliestColumn = getColumns(cellEntries).sorted().head();
        int latestRow = getRows(cellEntries).sorted().last();
        int earliestRow = getRows(cellEntries).sorted().head();
        int width = latestColumn - earliestColumn + 1;
        int height = latestRow - earliestRow + 1;
        return new Size(width, height);
    }

    private Vector<Integer> getRows(List<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getRow);
    }

    private Vector<Integer> getColumns(List<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getColumn);
    }

    private Vector<CellAddress> getCellAddresses(List<CellEntry> entryElements) {
        return Vector.ofAll(entryElements).map(CellEntry::getCellAddress);
    }
}