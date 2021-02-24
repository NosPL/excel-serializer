package com.noscompany.excel.sheet.entry.table;

import lombok.Value;

import java.util.List;

@Value
public class Tables {
    Table mainObjectTable;
    List<Table> complexFieldTables;
    List<Table> collectionFieldTables;
}