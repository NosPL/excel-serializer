package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;

import java.awt.*;
import java.util.List;

interface Record {
    List<CellEntry> draw(CellAddress startingPosition, Table.Layout tableLayout);

    Color getColor();
}
