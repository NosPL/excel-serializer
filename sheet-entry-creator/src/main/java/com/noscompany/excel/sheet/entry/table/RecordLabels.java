package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;

import java.util.List;

interface RecordLabels {
    List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout);

    short getColor();

    boolean isEmpty();
}
