package com.noscompany.excel.sheet.entry.schema;

import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class Schema {
    String name;
    List<SimpleType> simpleTypes;
    List<ComplexType> complexTypes;
    List<TypeCollection> typeCollections;

    public List<String> simpleFieldNames() {
        return simpleTypes
                .stream()
                .map(SimpleType::getName)
                .collect(toList());
    }

    public List<String> simpleFieldValues() {
        return simpleTypes
                .stream()
                .map(SimpleType::getValue)
                .collect(toList());
    }
}
