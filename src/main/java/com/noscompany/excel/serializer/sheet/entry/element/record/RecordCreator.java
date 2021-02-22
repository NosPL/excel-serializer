package com.noscompany.excel.serializer.sheet.entry.element.record;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.field.extractor.SimpleField;

import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.objectsInsideAreSimple;
import static com.noscompany.excel.serializer.sheet.entry.element.record.Record.record;
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
        return List.of(record(values, nameColor(), valuesColor()));
    }

    private List<Record> fromComplexCollection(Collection<?> collection) {
        return simpleFieldsFromObjectsInside(collection)
                .stream()
                .map(sf -> createRecord(collection, sf))
                .collect(toList());
    }

    private Record createRecord(Collection<?> collection, SimpleField sf) {
        List<String> values = collection.stream().map(sf::getValue).collect(toList());
        return record(sf.getName(), values, nameColor(), valuesColor());
    }

    private List<SimpleField> simpleFieldsFromObjectsInside(Collection<?> collection) {
        if (collection.isEmpty())
            return List.of();
        Object object = collection.iterator().next();
        return fieldExtractor.simpleFields(object);
    }

    private List<String> getValues(Collection<?> collection) {
        return collection
                .stream()
                .map(o -> o == null ? "" : o.toString())
                .collect(toList());
    }

    private short nameColor() {
        return config.getRecordNamesColor();
    }

    private short valuesColor() {
        return config.getRecordValuesColor();
    }
}