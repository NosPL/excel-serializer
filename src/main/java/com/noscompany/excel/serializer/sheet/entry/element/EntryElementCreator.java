package com.noscompany.excel.serializer.sheet.entry.element;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.CollectionField;
import com.noscompany.excel.serializer.field.extractor.ComplexField;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.sheet.entry.element.EntryElement.Label;
import com.noscompany.excel.serializer.sheet.entry.element.record.Record;
import com.noscompany.excel.serializer.sheet.entry.element.record.RecordCreator;
import io.vavr.control.Option;

import java.util.LinkedList;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.name;

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
        return EntryElementList
                .fromMainObject(entryFrom(object))
                .addEntriesFromComplexFields(entries(complexFieldsInside(object)))
                .addEntriesFromCollectionFields(entriesFrom(collectionFieldsInside(object)));
    }

    private EntryElement entryFrom(Object object) {
        Option<Label> label = config.isLabels() ? Option.of(objectLabel(object)) : Option.none();
        return getSingleValueEntryElement(object, label);
    }

    private List<EntryElement> entries(List<ComplexField> complexFields) {
        List<EntryElement> result = new LinkedList<>();
        for (ComplexField c : complexFields) {
            Option<Label> label = config.isLabels() ? Option.of(new Label(c.getLabel(), config.getLabelColor())) : Option.none();
            result.add(getSingleValueEntryElement(c.getObject(), label));
        }
        return result;
    }

    private List<EntryElement> entriesFrom(List<CollectionField> collectionFields) {
        List<EntryElement> result = new LinkedList<>();
        for (CollectionField cf : collectionFields) {
            Option<Label> label = config.isLabels() ? Option.of(label(cf)) : Option.none();
            List<Record> records = recordCreator.create(cf.getCollection());
            result.add((new EntryElement(label, records)));
        }
        return result;
    }

    private List<ComplexField> complexFieldsInside(Object object) {
        return fieldExtractor.complexFields(object);
    }

    private List<CollectionField> collectionFieldsInside(Object object) {
        return fieldExtractor.collectionFields(object);
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