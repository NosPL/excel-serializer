package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
class SimpleTypeCollection implements TypeCollection {
    String name;
    List<String> fieldValues;

    public List<String> getFieldNames() {
        return List.of();
    }

    public List<List<String>> getFieldValues() {
        return ofAll(fieldValues)
                .map(List::of)
                .toJavaList();
    }
}
