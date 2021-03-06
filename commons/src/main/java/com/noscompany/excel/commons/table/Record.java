package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;

import java.util.List;

interface Record {
    List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout);
}
