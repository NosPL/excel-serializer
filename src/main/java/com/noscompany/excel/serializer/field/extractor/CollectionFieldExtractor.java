package com.noscompany.excel.serializer.field.extractor;

import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.isCollection;
import static com.noscompany.excel.serializer.commons.ExcelUtils.name;

class CollectionFieldExtractor {

    <T> List<CollectionField> extract(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Vector.of(fields)
                .filter(field -> isCollection(field.getType()))
                .map(f -> createCollectionField(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private <T> CollectionField createCollectionField(T object, Field field) {
        field.setAccessible(true);
        Collection collection = (Collection) field.get(object);
        if (collection == null || collection.isEmpty())
            collection = List.of();
        return new CollectionField(name(field), collection);
    }
}
