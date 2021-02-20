package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Size;
import io.vavr.collection.Vector;

import java.util.List;

class CellEntrySizeCalculating {

    Size calculate(List<CellEntry> cellEntries) {
        int latestColumn = getCellAddresses(cellEntries).map(CellAddress::getColumn).sorted().last();
        int earliestColumn = getCellAddresses(cellEntries).map(CellAddress::getColumn).sorted().reverse().last();
        int latestRow = getCellAddresses(cellEntries).map(CellAddress::getRow).sorted().last();
        int earliestRow = getCellAddresses(cellEntries).map(CellAddress::getRow).sorted().reverse().last();
        int width = latestColumn - earliestColumn + 1;
        int height = latestRow - earliestRow + 1;
        return new Size(width, height);
    }

    private Vector<CellAddress> getCellAddresses(List<CellEntry> entryElements) {
        return Vector.ofAll(entryElements).map(CellEntry::getCellAddress);
    }
}