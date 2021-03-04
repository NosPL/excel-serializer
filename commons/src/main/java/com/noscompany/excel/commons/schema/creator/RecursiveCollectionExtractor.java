package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.schema.ValueCollection;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import lombok.Value;

import java.util.Collection;

import static io.vavr.collection.Vector.ofAll;

@Value
class RecursiveCollectionExtractor {
    Config config;
    SimpleValueExtractor simpleValueExtractor = new SimpleValueExtractor();
    SimpleCollectionExtractor simpleCollectionExtractor = new SimpleCollectionExtractor();
    ComplexCollectionExtractor complexCollectionExtractor;

    Vector<ValueCollection> extractRecursively(Collection<?> collection) {
        if (config.isAllowNestedCollections())
            return extractRecursivelySimpleValueCollections(collection)
                    .prependAll(extractRecursivelyComplexValueCollections(collection));
        else
            return Vector.empty();
    }

    private Vector<ValueCollection> extractRecursivelySimpleValueCollections(Collection<?> collection) {
        Vector<ValueCollection> simpleValueCollections = ofAll(collection).flatMap(simpleCollectionExtractor::extract);
        return flatten(simpleValueCollections);
    }

    private Vector<ValueCollection> extractRecursivelyComplexValueCollections(Collection<?> collection) {
        Vector<ValueCollection> valueCollections = ofAll(collection).flatMap(complexCollectionExtractor::extract);
        return flatten(valueCollections);
    }

    private Vector<ValueCollection> flatten(Vector<ValueCollection> simpleValueCollections) {
        if (config.isFlattenNestedCollection()) {
            Vector<ValueCollection> result = simpleValueCollections
                    .groupBy(ValueCollection::getName)
                    .flatMap(this::flatten)
                    .toVector();
            return result;
        }
        return simpleValueCollections;
    }

    private Vector<ValueCollection> flatten(Tuple2<String, Vector<ValueCollection>> tuple) {
        return tuple._2()
                .reduceOption(ValueCollection::concat)
                .toVector();
    }
}
