package com.noscompany.excel.serializer.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter(PACKAGE)
public class Record {
    private final List<String> values;

    public static Record r(String... values) {
        return new Record(Arrays.asList(values));
    }
}
