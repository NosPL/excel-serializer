package com.noscompany.excel.serializer.sheet.entry.element.extractor;

import com.noscompany.excel.serializer.annotations.ExcelEmbedded;
import com.noscompany.excel.serializer.annotations.ExcelObject;
import com.noscompany.excel.serializer.commons.ExcelUtils;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.getAnnotation;


class ComplexFieldExtractor {

    List<ComplexField> extractFrom(CollectionField collectionField) {
        Collection collection = collectionField.getCollection();
        if (collection.isEmpty()) return List.of();
        Object next = collection.iterator().next();
        return extract(next);
    }

    List<ComplexField> extract(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Vector
                .of(fields)
                .filter(this::isComplex)
                .map(field -> complexField(field, object))
                .toJavaList();
    }

    private boolean isComplex(Field field) {
        return ExcelUtils.isComplex(field.getType())
                && (
                getAnnotation(field.getType(), ExcelObject.class).isDefined() ||
        getAnnotation(field, ExcelEmbedded.class).isDefined());
    }

    @SneakyThrows
    private ComplexField complexField(Field field, Object object) {
        field.setAccessible(true);
        String label = getLabel(field);
        return new ComplexField(label, field.get(object));
    }

    private String getLabel(Field field) {
        return getAnnotation(field, ExcelEmbedded.class)
                .map(ExcelEmbedded::label)
                .orElse(getAnnotation(field.getType(), ExcelObject.class)
                .map(ExcelObject::label))
                .getOrElse(field.getName());
    }
}
