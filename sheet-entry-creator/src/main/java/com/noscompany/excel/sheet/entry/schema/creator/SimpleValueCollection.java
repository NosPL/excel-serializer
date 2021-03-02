package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.sheet.entry.schema.ValueCollection;
import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
class SimpleValueCollection implements ValueCollection {
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

    @Override
    public ValueCollection concat(ValueCollection valueCollection) {
        if (valueCollection instanceof SimpleValueCollection) {
            fieldValues.addAll(((SimpleValueCollection) valueCollection).fieldValues);
        }
        return this;
    }
}
