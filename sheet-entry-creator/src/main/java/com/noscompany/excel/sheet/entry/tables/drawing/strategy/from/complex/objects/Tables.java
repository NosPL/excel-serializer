package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.sheet.entry.table.Table;
import lombok.Value;

import java.util.List;

@Value
class Tables {
    Table mainObjectTable;
    List<Table> complexFieldTables;
    List<Table> collectionFieldTables;
}