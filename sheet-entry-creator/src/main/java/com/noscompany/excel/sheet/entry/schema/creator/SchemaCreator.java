package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.sheet.entry.schema.ComplexType;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.SimpleType;
import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import io.vavr.collection.Vector;

import java.util.List;
import java.util.Objects;

public class SchemaCreator {
    private final Config config;
    private final SimpleTypeExtractor simpleTypeExtractor;
    private final ComplexTypeExtractor complexTypeExtractor;
    private final ComplexTypeCollectionExtractor complexTypeCollectionExtractor;
    private final SimpleTypeCollectionExtractor simpleTypeCollectionExtractor;

    public SchemaCreator(Config config) {
        this.config = config;
        simpleTypeExtractor = new SimpleTypeExtractor();
        complexTypeExtractor = new ComplexTypeExtractor();
        complexTypeCollectionExtractor = new ComplexTypeCollectionExtractor(config);
        simpleTypeCollectionExtractor = new SimpleTypeCollectionExtractor();
    }

    public Schema create(Object object) {
        return new Schema(
                nameOf(object),
                simpleFieldsFrom(object),
                complexFieldsFrom(object),
                collectionFieldsFrom(object)
        );
    }

    private List<TypeCollection> collectionFieldsFrom(Object object) {
        return complexCollectionFieldsFrom(object)
                .appendAll(simpleCollectionFieldsFrom(object))
                .toJavaList();
    }

    private Vector<TypeCollection> complexCollectionFieldsFrom(Object object) {
        return Vector.ofAll(complexTypeCollectionExtractor.extract(object));
    }

    private List<TypeCollection> simpleCollectionFieldsFrom(Object object) {
        return simpleTypeCollectionExtractor.extract(object);
    }

    public List<SimpleType> simpleFieldsFrom(Object object) {
        return simpleTypeExtractor.extract(object);
    }

    public List<ComplexType> complexFieldsFrom(Object object) {
        return complexTypeExtractor.extract(object);
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
