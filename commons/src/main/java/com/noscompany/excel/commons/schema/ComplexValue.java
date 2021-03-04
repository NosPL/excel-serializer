package com.noscompany.excel.commons.schema;

import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
public class ComplexValue {
    String name;
    List<SimpleValue> simpleValues;

    public List<String> fieldNames() {
        return ofAll(simpleValues)
                .map(SimpleValue::getName)
                .toJavaList();
    }

    public List<String> fieldValues() {
        return ofAll(simpleValues)
                .map(SimpleValue::getValue)
                .toJavaList();
    }

}
