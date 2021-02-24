package com.noscompany.excel.sheet.entry.schema;

import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
public class ComplexType {
    String name;
    List<SimpleType> simpleTypes;

    public List<String> fieldNames() {
        return ofAll(simpleTypes)
                .map(SimpleType::getName)
                .toJavaList();
    }

    public List<String> fieldValues() {
        return ofAll(simpleTypes)
                .map(SimpleType::getValue)
                .toJavaList();
    }

}
