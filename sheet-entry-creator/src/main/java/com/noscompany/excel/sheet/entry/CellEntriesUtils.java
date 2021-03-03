package com.noscompany.excel.sheet.entry;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import com.noscompany.excel.commons.SurfaceSize;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import static io.vavr.collection.Vector.ofAll;

public class CellEntriesUtils {

    public static SurfaceSize surfaceSizeOf(Iterable<CellEntry> cellEntries) {
        if (!cellEntries.iterator().hasNext())
            return new SurfaceSize(0, 0);
        int highestColumnIndex = highest(columnIndexFrom(cellEntries)).getOrElse(0);
        int smallestColumnIndex = smallest(columnIndexFrom(cellEntries)).getOrElse(0);
        int highestRowIndex = highest(rowIndexFrom(cellEntries)).getOrElse(0);
        int smallestRowIndex = smallest(rowIndexFrom(cellEntries)).getOrElse(0);
        int width = highestColumnIndex - smallestColumnIndex + 1;
        int height = highestRowIndex - smallestRowIndex + 1;
        return new SurfaceSize(width, height);
    }

    public static <T> Option<T> smallest(Iterable<T> iterable) {
        return ofAll(iterable).sorted().headOption();
    }

    public static <T> Option<T> highest(Iterable<T> iterable) {
        return ofAll(iterable).sorted().lastOption();
    }

    public static Iterable<Integer> rowIndexFrom(Iterable<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getRow);
    }

    public static Iterable<Integer> columnIndexFrom(Iterable<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getColumn);
    }

    public static Vector<CellAddress> getCellAddresses(Iterable<CellEntry> entryElements) {
        return ofAll(entryElements).map(CellEntry::getCellAddress);
    }
}