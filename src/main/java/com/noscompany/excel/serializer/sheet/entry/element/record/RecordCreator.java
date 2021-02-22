package com.noscompany.excel.serializer.sheet.entry.element.record;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.field.extractor.SimpleField;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;

import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.objectsInsideAreSimple;
import static com.noscompany.excel.serializer.sheet.entry.element.record.Record.record;
import static io.vavr.collection.Vector.ofAll;
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
        return ofAll(collection)
                .flatMap(fieldExtractor::simpleFields)
                .groupBy(SimpleField::getName)
                .map(this::tupleToRecord)
                .toJavaList();
    }

    private Record tupleToRecord(Tuple2<String, Vector<SimpleField>> tuple) {
        String recordName = tuple._1;
        List<String> recordValues = tuple._2.map(SimpleField::getValue).toJavaList();
        return Record.record(recordName, recordValues, nameColor(), valuesColor());
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