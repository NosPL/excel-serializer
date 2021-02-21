package com.noscompany.excel.serializer.field.extractor;

import java.util.List;

public class FieldExtractor {
    private final SimpleFieldExtractor simpleFieldExtractor;
    private final ComplexFieldExtractor complexFieldExtractor;
    private final CollectionFieldExtractor collectionFieldExtractor;

    public FieldExtractor() {
        simpleFieldExtractor = new SimpleFieldExtractor();
        complexFieldExtractor = new ComplexFieldExtractor();
        collectionFieldExtractor = new CollectionFieldExtractor();
    }

    public List<SimpleField> simpleFields(Object object) {
        return simpleFieldExtractor.extract(object);
    }

    public List<ComplexField> complexFields(Object object) {
        return complexFieldExtractor.extract(object);
    }

    public List<CollectionField> collectionFields(Object object) {
        return collectionFieldExtractor.extract(object);
    }
}
