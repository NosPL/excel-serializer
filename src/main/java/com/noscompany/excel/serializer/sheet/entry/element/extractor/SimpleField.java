package com.noscompany.excel.serializer.sheet.entry.element.extractor;


import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Field;

@Value
public class SimpleField {
    String name;
    Field field;

    @SneakyThrows
    public String getValue(Object object) {
        field.setAccessible(true);
        try {
            return field.get(object).toString();
        } catch (Exception e) {
            return "";
        }
    }
}

