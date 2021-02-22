package com.noscompany.excel.serializer.field.extractor;

public class SimpleField {
    private final String name;
    private final String value;

    public SimpleField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}