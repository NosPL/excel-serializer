package com.noscompany.excel.serializer.sheet.entry.element.record;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.field.extractor.SimpleField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
        if (collection.isEmpty())
            return List.of();
        Object object = collection.iterator().next();
        List<SimpleField> simpleFields = fieldExtractor.simpleFields(object);
        List<Record> result = new LinkedList<>();
        for (SimpleField sf : simpleFields) {
            List<String> values = collection.stream().map(sf::getValue).collect(toList());
            result.add(Record.record(sf.getName(), values, nameColor(), valuesColor()));
        }
        return result;
    }
}