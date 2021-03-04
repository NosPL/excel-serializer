package com.noscompany.excel.commons;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@AllArgsConstructor
public class CellEntries {
    private final List<CellEntry> cellEntryList;

    public static CellEntries empty() {
        return new CellEntries(new LinkedList<>());
    }

    public List<CellEntry> get() {
        return cellEntryList;
    }

    public SurfaceSize getSurfaceSize() {
        return surfaceSizeOf(cellEntryList);
    }

    public CellEntries concat(CellEntry cellEntry) {
        cellEntryList.add(cellEntry);
        return this;
    }

    public CellEntries concat(List<CellEntry> cellEntries) {
        cellEntryList.addAll(cellEntries);
        return this;
    }

    public CellEntries concat(CellEntries cellEntries) {
        cellEntryList.addAll(cellEntries.get());
        return this;
    }

    private SurfaceSize surfaceSizeOf(List<CellEntry> cellEntries) {
        if (cellEntries.isEmpty())
            return new SurfaceSize(0, 0);
        int highestColumnIndex = highest(columnIndexFrom(cellEntries)).getOrElse(0);
        int smallestColumnIndex = smallest(columnIndexFrom(cellEntries)).getOrElse(0);
        int highestRowIndex = highest(rowIndexFrom(cellEntries)).getOrElse(0);
        int smallestRowIndex = smallest(rowIndexFrom(cellEntries)).getOrElse(0);
        int width = highestColumnIndex - smallestColumnIndex + 1;
        int height = highestRowIndex - smallestRowIndex + 1;
        return new SurfaceSize(width, height);
    }

    private <T> Option<T> smallest(Iterable<T> iterable) {
        return ofAll(iterable).sorted().headOption();
    }

    private <T> Option<T> highest(Iterable<T> iterable) {
        return ofAll(iterable).sorted().lastOption();
    }

    private Iterable<Integer> rowIndexFrom(Iterable<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getRow);
    }

    private Iterable<Integer> columnIndexFrom(Iterable<CellEntry> cellEntries) {
        return getCellAddresses(cellEntries).map(CellAddress::getColumn);
    }

    private Vector<CellAddress> getCellAddresses(Iterable<CellEntry> entryElements) {
        return ofAll(entryElements).map(CellEntry::getCellAddress);
    }
}
