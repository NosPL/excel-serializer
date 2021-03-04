package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.commons.schema.ComplexValue;
import com.noscompany.excel.commons.schema.Schema;
import com.noscompany.excel.commons.schema.SimpleValue;
import com.noscompany.excel.commons.schema.ValueCollection;
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

    public Schema create(JessyObject object) {
        return new Schema(
                nameOf(object),
                simpleFieldsFrom(object),
                complexFieldsFrom(object),
                collectionFieldsFrom(object)
        );
    }

    private List<ValueCollection> collectionFieldsFrom(JessyObject object) {
        return complexCollectionFieldsFrom(object)
                .appendAll(simpleCollectionFieldsFrom(object))
                .toJavaList();
    }

    private Vector<ValueCollection> complexCollectionFieldsFrom(JessyObject object) {
        return Vector.ofAll(complexCollectionExtractor.extract(object));
    }

    private List<ValueCollection> simpleCollectionFieldsFrom(JessyObject object) {
        return simpleCollectionExtractor.extract(object);
    }

    private List<SimpleValue> simpleFieldsFrom(JessyObject object) {
        return simpleValueExtractor.extract(object);
    }

    private List<ComplexValue> complexFieldsFrom(JessyObject object) {
        return complexValueExtractor.extract(object);
    }

    private String nameOf(JessyObject object) {
        Class<?> clazz = object.getClass();
        String result = SchemaUtils.getAnnotation(clazz, ClassName.class)
                .map(ClassName::value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(clazz.getSimpleName());
        return result;
    }
}