package com.noscompany.excel.serializer.sheet.entry.element;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.CollectionField;
import com.noscompany.excel.serializer.field.extractor.ComplexField;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElement.Label;
import com.noscompany.excel.serializer.sheet.entry.element.record.Record;
import com.noscompany.excel.serializer.sheet.entry.element.record.RecordCreator;
import io.vavr.control.Option;

import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.name;
import static java.util.stream.Collectors.toList;

public class EntryElementCreator {
    private final Config config;
    private final RecordCreator recordCreator;
    private final FieldExtractor fieldExtractor;

    public EntryElementCreator(Config config) {
        this.config = config;
        recordCreator = new RecordCreator(config);
        fieldExtractor = new FieldExtractor();
    }

    public EntryElementList create(Object object) {
        List<ComplexField> complexFields = fieldExtractor.complexFields(object);
        List<CollectionField> collectionFields = fieldExtractor.collectionFields(object);
        return new EntryElementList(
                entryFromMain(object),
                entries(complexFields),
                entriesFrom(collectionFields));
    }

    private EntryElement entryFromMain(Object object) {
        Option<Label> label = config.isLabels() ? Option.of(objectLabel(object)) : Option.none();
        return getSingleValueEntryElement(object, label);
    }

    private List<EntryElement> entries(List<ComplexField> complexFields) {
        return complexFields.stream()
                .map(complexField -> getSingleValueEntryElement(complexField.getObject(), label(complexField)))
                .collect(toList());
    }

    private Option<Label> label(ComplexField c) {
        return config.isLabels() ? Option.of(new Label(c.getLabel(), config.getLabelColor())) : Option.none();
    }

    private List<EntryElement> entriesFrom(List<CollectionField> collectionFields) {
        return collectionFields.stream()
                .map(cf -> {
                    Option<Label> label = config.isLabels() ? Option.of(label(cf)) : Option.none();
                    List<Record> records = recordCreator.create(cf.getCollection());
                    return new EntryElement(label, records);
                })
                .collect(toList());
    }

    private EntryElement getSingleValueEntryElement(Object object, Option<Label> label) {
        List<Object> arg = object == null ? List.of() : List.of(object);
        List<Record> records = recordCreator.create(arg);
        return new EntryElement(label, records);
    }

    private Label label(CollectionField cf) {
        return new Label(cf.getName(), config.getLabelColor());
    }

    private Label objectLabel(Object object) {
        String labelValue = name(object.getClass());
        return new Label(labelValue, config.getLabelColor());
    }
}