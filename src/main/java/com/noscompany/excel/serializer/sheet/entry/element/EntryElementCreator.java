package com.noscompany.excel.serializer.sheet.entry.element;

import com.noscompany.excel.serializer.annotations.ExcelObject;
import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.sheet.entry.element.extractor.*;
import com.noscompany.excel.serializer.sheet.entry.element.record.Record;
import com.noscompany.excel.serializer.sheet.entry.element.record.RecordCreator;
import io.vavr.control.Option;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.ExcelUtils.getAnnotation;

public class EntryElementCreator {
    private final Config config;
    private final RecordCreator recordCreator;
    private final FieldExtractor fieldExtractor;

    public EntryElementCreator(Config config) {
        this.config = config;
        recordCreator = new RecordCreator();
        fieldExtractor = new FieldExtractor();
    }

    public EntryElementList create(Object object) {
        return EntryElementList
                .fromMainObject(entryFrom(object))
                .addEntriesFromComplexFields(entries(complexFieldsInside(object)))
                .addEntriesFromCollectionFields(entriesFrom(collectionFieldsInside(object)));
    }

    private EntryElement entryFrom(Object object) {
        Option<EntryElement.Label> label = config.isLabels() ? Option.of(objectLabel(object)) : Option.none();
        return getSingleValueEntryElement(object, label);
    }

    private List<EntryElement> entries(List<ComplexField> complexFields) {
        List<EntryElement> result = new LinkedList<>();
        for (ComplexField c : complexFields) {
            Option<EntryElement.Label> label = config.isLabels() ? Option.of(new EntryElement.Label(c.getLabel(), config.getLabelColor())) : Option.none();
            result.add(getSingleValueEntryElement(c.getObject(), label));
        }
        return result;
    }

    private List<EntryElement> entriesFrom(List<CollectionField> collectionFields) {
        List<EntryElement> result = new LinkedList<>();
        for (CollectionField cf : collectionFields) {
            Option<EntryElement.Label> label = config.isLabels() ? Option.of(label(cf)) : Option.none();
            Short fieldNamesValues = config.getRecordNamesColor();
            Short fieldValuesColor = config.getRecordValuesColor();
            List<Record> records = recordCreator.create(cf.getCollection(), fieldNamesValues, fieldValuesColor);
            result.add((new EntryElement(label, records)));
        }
        return result;
    }

    private List<ComplexField> complexFieldsInside(Object object) {
        return fieldExtractor.complexFields(object);
    }

    private List<CollectionField> collectionFieldsInside(Object object) {
        List<CollectionField> collectionFields = fieldExtractor.collectionFields(object);
        return collectionFields;
    }

    private EntryElement getSingleValueEntryElement(Object object, Option<EntryElement.Label> label) {
        Short fieldNamesColor = config.getRecordNamesColor();
        Short fieldValuesColor = config.getRecordValuesColor();
        List<Object> arg = object == null ? List.of() : List.of(object);
        List<Record> records = recordCreator.create(arg, fieldNamesColor, fieldValuesColor);
        return new EntryElement(label, records);
    }

    private EntryElement.Label label(CollectionField cf) {
        return new EntryElement.Label(cf.getName(), config.getLabelColor());
    }

    private EntryElement.Label objectLabel(Object object) {
        String labelValue = getAnnotation(object.getClass(), ExcelObject.class)
                .map(ExcelObject::label)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(object.getClass().getSimpleName());
        return new EntryElement.Label(labelValue, config.getLabelColor());
    }
}