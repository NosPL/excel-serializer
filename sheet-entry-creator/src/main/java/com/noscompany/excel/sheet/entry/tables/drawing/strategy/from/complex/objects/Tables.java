package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.table.TablesSequence;
import lombok.Value;

@Value
class Tables {
    TablesSequence singleValueTables;
    TablesSequence tablesFromCollection;
}