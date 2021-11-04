package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.schema.ComplexValue;
import com.noscompany.excel.commons.schema.SimpleValue;
import com.noscompany.excel.commons.schema.ValueCollection;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.commons.schema.creator.SchemaUtils.*;
import static io.vavr.collection.Vector.of;
import static io.vavr.collection.Vector.ofAll;

@Value
class ComplexCollectionExtractor {
    Config config;
    SimpleValueExtractor simpleValueExtractor = new SimpleValueExtractor();
    ComplexValueExtractor complexValueExtractor = new ComplexValueExtractor();
    SimpleCollectionExtractor simpleCollectionExtractor = new SimpleCollectionExtractor();

    List<ValueCollection> extract(Object object) {
        return ofAll(fieldsFromObject(object))
                .filter(SchemaUtils::isCollection)
                .flatMap(f -> extract(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private List<ValueCollection> extract(Object object, Field field) {
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

    private List<ValueCollection> extract(Field field, Collection<?> collection) {
        ValueCollection valueCollection = toValueCollection(field, collection);
        return of(valueCollection)
                .appendAll(complexValuesGroupedByName(collection))
                .toJavaList();
    }

    private ValueCollection toValueCollection(Field field, Collection<?> collection) {
        List<ComplexValue> complexValues = ofAll(collection).map(this::toComplexValue).toJavaList();
        return new ComplexCollection(nameOf(field), complexValues);
    }

    private ComplexValue toComplexValue(Object object) {
        List<SimpleValue> simpleValues = simpleValueExtractor.extract(object);
        Class<?> clazz = object.getClass();
        return new ComplexValue(nameOf(clazz), simpleValues);
    }

    private Vector<ValueCollection> complexValuesGroupedByName(Collection<?> collection) {
        return ofAll(collection)
                .flatMap(this::extractComplexValues)
                .groupBy(ComplexValue::getName)
                .map(this::toValueCollections)
                .toVector();
    }

    private ValueCollection toValueCollections(Tuple2<String, Vector<ComplexValue>> t) {
        return new ComplexCollection(t._1, t._2.toJavaList());
    }

    private List<ComplexValue> extractComplexValues(Object object) {
        return complexValueExtractor.extract(object);
    }
}
