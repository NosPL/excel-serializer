package com.noscompany.excel.commons;

import lombok.Value;

import java.util.List;

@Value
public class SheetEntry {
    SurfaceSize surfaceSize;
    List<CellEntry> cellEntries;
    CellAddress startingPoint;
}