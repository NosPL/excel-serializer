package com.noscompany.excel.serializer.record;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import io.vavr.collection.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.ExcelUtils.objectsInsideAreSimple;
import static java.util.stream.Collectors.toList;

public class RecordCreator {
    private final Config config;
    private final FieldExtractor fieldExtractor;

    public RecordCreator(Config config) {
        this.config = config;
        this.fieldExtractor = new FieldExtractor();
    }

    public List<Record> create(Collection<?> collection) {
        if (objectsInsideAreSimple(collection)) {
            return fromSimple(collection);
        } else
            return fromComplexCollection(collection);
    }

    private List<Record> fromSimple(Collection<?> collection) {
        List<String> values = getValues(collection);
        return List.of(Record.record(values, nameColor(), valuesColor()));
    }

    private short valuesColor() {
        return config.getRecordValuesColor();
    }

    private short nameColor() {
        return config.getRecordNamesColor();
    }

    private List<String> getValues(Collection<?> collection) {
        return collection
                .stream()
                .map(o -> o == null ? "" : o.toString())
                .collect(toList());
    }

    private List<Record> fromComplexCollection(Collection<?> collection) {
        return Vector.ofAll(collection)
                .filter(Objects::nonNull)
                .flatMap(fieldExtractor::simpleFields)
                .map(simpleField -> {
                    List<String> values = collection.stream().map(simpleField::getValue).collect(toList());
                    return Record.record(simpleField.getName(), values, nameColor(), valuesColor());
                })
                .toJavaList();
    }
}