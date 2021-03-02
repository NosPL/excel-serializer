package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.sheet.entry.schema.ComplexValue;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.SimpleValue;
import com.noscompany.excel.sheet.entry.schema.ValueCollection;
import io.vavr.collection.Vector;

import java.util.List;
import java.util.Objects;

public class SchemaCreator {
    private final SimpleValueExtractor simpleValueExtractor;
    private final ComplexValueExtractor complexValueExtractor;
    private final ComplexCollectionExtractor complexCollectionExtractor;
    private final SimpleCollectionExtractor simpleCollectionExtractor;

    public SchemaCreator(Config config) {
        simpleValueExtractor = new SimpleValueExtractor();
        complexValueExtractor = new ComplexValueExtractor();
        complexCollectionExtractor = new ComplexCollectionExtractor(config);
        simpleCollectionExtractor = new SimpleCollectionExtractor();
    }

    public Schema create(Object object) {
        return new Schema(
                nameOf(object),
                simpleFieldsFrom(object),
                complexFieldsFrom(object),
                collectionFieldsFrom(object)
        );
    }

    private List<ValueCollection> collectionFieldsFrom(Object object) {
        return complexCollectionFieldsFrom(object)
                .appendAll(simpleCollectionFieldsFrom(object))
                .toJavaList();
    }

    private Vector<ValueCollection> complexCollectionFieldsFrom(Object object) {
        return Vector.ofAll(complexCollectionExtractor.extract(object));
    }

    private List<ValueCollection> simpleCollectionFieldsFrom(Object object) {
        return simpleCollectionExtractor.extract(object);
    }

    public List<SimpleValue> simpleFieldsFrom(Object object) {
        return simpleValueExtractor.extract(object);
    }

    public List<ComplexValue> complexFieldsFrom(Object object) {
        return complexValueExtractor.extract(object);
    }

    private String nameOf(Object object) {
        Class<?> clazz = object.getClass();
        String result = SchemaUtils.getAnnotation(clazz, ClassName.class)
                .map(ClassName::value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(clazz.getSimpleName());
        return result;
    }
}
