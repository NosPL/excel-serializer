package com.noscompany.excel.commons.schema;

import java.util.List;

public interface ValueCollection {
    String getName();

    List<String> getFieldNames();

    List<List<String>> getFieldValues();

    ValueCollection concat(ValueCollection valueCollection);
}
