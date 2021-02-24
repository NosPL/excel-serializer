package com.noscompany.excel.sheet.entry.schema;

import java.util.List;

public interface TypeCollection {
    String getName();
    List<String> getFieldNames();
    List<List<String>> getFieldValues();
}
