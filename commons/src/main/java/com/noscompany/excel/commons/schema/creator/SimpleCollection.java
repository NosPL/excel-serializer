package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.schema.ValueCollection;
import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
class SimpleCollection implements ValueCollection {
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
        if (valueCollection instanceof SimpleCollection)
            fieldValues.addAll(((SimpleCollection) valueCollection).fieldValues);
        return this;
    }
}
