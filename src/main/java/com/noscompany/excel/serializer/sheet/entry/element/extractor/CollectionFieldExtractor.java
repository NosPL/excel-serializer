package com.noscompany.excel.serializer.sheet.entry.element.extractor;

import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.ExcelUtils.getAnnotation;
import static com.noscompany.excel.serializer.commons.ExcelUtils.isCollection;

class CollectionFieldExtractor {

    <T> List<CollectionField> extract(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Vector.of(fields)
                .filter(field -> isCollection(field.getType()))
                .map(f -> createCollectionField(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private <T> CollectionField createCollectionField(T object, Field f) {
        f.setAccessible(true);
        Collection collection = (Collection) f.get(object);
        if (collection == null || collection.isEmpty())
            collection = List.of();
        return new CollectionField(fieldName(f), collection);
    }

    private String fieldName(Field f) {
        return getAnnotation(f, ExcelSimpleField.class)
                .map(ExcelSimpleField::label)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(f.getName());
    }
}
