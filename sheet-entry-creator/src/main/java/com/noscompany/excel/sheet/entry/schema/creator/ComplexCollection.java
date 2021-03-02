package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.sheet.entry.schema.ComplexValue;
import com.noscompany.excel.sheet.entry.schema.ValueCollection;
import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
class ComplexCollection implements ValueCollection {
    String name;
    List<ComplexValue> complexValues;

    public List<String> getFieldNames() {
        return ofAll(complexValues)
                .headOption()
                .map(ComplexValue::fieldNames)
                .getOrElse(List.of());
    }

    public List<List<String>> getFieldValues() {
        return ofAll(complexValues)
                .map(ComplexValue::fieldValues)
                .toJavaList();
    }

    @Override
    public ValueCollection concat(ValueCollection valueCollection) {
        if (valueCollection instanceof ComplexCollection) {
            complexValues.addAll(((ComplexCollection) valueCollection).complexValues);
        }
        return this;
    }
}
