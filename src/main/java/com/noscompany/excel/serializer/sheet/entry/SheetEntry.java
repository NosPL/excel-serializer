package com.noscompany.excel.serializer.sheet.entry;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import com.noscompany.excel.serializer.commons.Size;
import lombok.Value;

import java.util.List;

@Value
public class SheetEntry {
    Size size;
    List<CellEntry> cellEntries;
    CellAddress startingPoint;
}
