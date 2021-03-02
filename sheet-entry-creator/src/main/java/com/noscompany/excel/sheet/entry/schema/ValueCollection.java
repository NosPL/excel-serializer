package com.noscompany.excel.sheet.entry.schema;

import java.util.List;

public interface ValueCollection {
    String getName();

    List<String> getFieldNames();

    List<List<String>> getFieldValues();

    ValueCollection concat(ValueCollection valueCollection);
}
