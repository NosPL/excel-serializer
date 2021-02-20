package com.noscompany.excel.serializer.sheet.entry.element.record;

import com.noscompany.excel.serializer.sheet.entry.element.extractor.FieldExtractor;
import com.noscompany.excel.serializer.sheet.entry.element.extractor.SimpleField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.objectsInsideAreSimple;
import static java.util.stream.Collectors.toList;

public class RecordCreator {
    private final FieldExtractor fieldExtractor;

    public RecordCreator() {
        this.fieldExtractor = new FieldExtractor();
    }

    public <T> List<Record> create(Collection<?> collection, Short fieldNamesColor, Short fieldValuesColor) {
        if (objectsInsideAreSimple(collection)) {
            return fromSimple(collection, fieldNamesColor, fieldValuesColor);
        } else
            return fromComplexCollection(collection, fieldNamesColor, fieldValuesColor);
    }

    private List<Record> fromSimple(Collection<?> collection, Short fieldNamesColor, Short fieldValuesColor) {
        List<String> values = getValues(collection);
        return List.of(Record.record(values, fieldNamesColor, fieldValuesColor));
    }

    private List<String> getValues(Collection<?> collection) {
        return collection
                .stream()
                .map(o -> o == null ? "" : o.toString())
                .collect(toList());
    }

    private List<Record> fromComplexCollection(Collection<?> collection, Short fieldNamesColor, Short fieldValuesColor) {
        List<Record> result = new LinkedList<>();
        List<SimpleField> simpleFields = fieldExtractor.simpleFields(collection);
        for (SimpleField sf : simpleFields) {
            List<String> values = collection.stream().map(sf::getValue).collect(toList());
            result.add(Record.record(sf.getName(), values, fieldNamesColor, fieldValuesColor));
        }
        return result;
    }
}