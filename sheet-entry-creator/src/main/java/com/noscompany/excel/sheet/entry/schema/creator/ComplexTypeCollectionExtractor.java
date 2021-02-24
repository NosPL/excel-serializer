package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.sheet.entry.schema.ComplexType;
import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils.*;
import static io.vavr.collection.Vector.ofAll;

@Value
class ComplexTypeCollectionExtractor {
    Config config;
    SimpleTypeExtractor simpleTypeExtractor = new SimpleTypeExtractor();
    ComplexTypeExtractor complexTypeExtractor = new ComplexTypeExtractor();
    SimpleTypeCollectionExtractor simpleTypeCollectionExtractor = new SimpleTypeCollectionExtractor();

    List<TypeCollection> extract(Object object) {
        return ofAll(fieldsFromObject(object))
                .filter(SchemaUtils::isCollection)
                .flatMap(f -> extract(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private List<TypeCollection> extract(Object object, Field field) {
        field.setAccessible(true);
        Collection<?> collection = (Collection<?>) field.get(object);
        if (collection == null || collection.isEmpty())
            return List.of();
        else {
            Object next = collection.iterator().next();
            if (isComplex(next.getClass())) {
                return extract(field, collection);
            } else
                return List.of();
        }
    }

    private List<TypeCollection> extract(Field field, Collection<?> collection) {
        if (config.isAllowNestedCollections())
            return extractRecursive(field, collection);
        else
            return extractNonRecursive(field, collection);
    }

    private List<TypeCollection> extractNonRecursive(Field field, Collection<?> collection) {
        List<ComplexType> complexTypes = ofAll(collection).map(this::toComplexField).toJavaList();
        return List.of(new ComplexTypeCollection(nameOf(field), complexTypes));
    }

    private List<TypeCollection> extractRecursive(Field field, Collection<?> collection) {
        List<ComplexType> complexTypes = ofAll(collection).map(this::toComplexField).toJavaList();
        TypeCollection typeCollection = new ComplexTypeCollection(nameOf(field), complexTypes);
        Vector<TypeCollection> collectionFields = ofAll(collection).flatMap(simpleTypeCollectionExtractor::extract);
        Vector<TypeCollection> collectionFields1 = ofAll(collection).flatMap(this::extract);
        return ofAll(collection)
                .flatMap(complexTypeExtractor::extract)
                .groupBy(ComplexType::getName)
                .map(this::toCollectionFields)
                .prepend(typeCollection)
                .appendAll(collectionFields)
                .appendAll(collectionFields1)
                .toJavaList();
    }

    private TypeCollection toCollectionFields(Tuple2<String, Vector<ComplexType>> t) {
        return new ComplexTypeCollection(t._1, t._2.toJavaList());
    }

    private ComplexType toComplexField(Object object) {
        return new ComplexType(nameOf(object.getClass()), simpleTypeExtractor.extract(object));
    }
}
