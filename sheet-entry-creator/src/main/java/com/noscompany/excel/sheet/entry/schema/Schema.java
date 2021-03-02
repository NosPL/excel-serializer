package com.noscompany.excel.sheet.entry.schema;

import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class Schema {
    String name;
    List<SimpleValue> simpleValues;
    List<ComplexValue> complexValues;
    List<ValueCollection> valueCollections;

    public List<String> simpleFieldNames() {
        return simpleValues
                .stream()
                .map(SimpleValue::getName)
                .collect(toList());
    }

    public List<String> simpleFieldValues() {
        return simpleValues
                .stream()
                .map(SimpleValue::getValue)
                .collect(toList());
    }
}
