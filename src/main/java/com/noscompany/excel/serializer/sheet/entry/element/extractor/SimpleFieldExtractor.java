package com.noscompany.excel.serializer.sheet.entry.element.extractor;

import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.ExcelUtils.getAnnotation;

@AllArgsConstructor
class SimpleFieldExtractor {

    List<SimpleField> extract(Collection<?> collection) {
        return Vector
                .ofAll(fieldsFrom(collection))
                .filter(this::isSimple)
                .map(f -> new SimpleField(name(f), f))
                .toJavaList();
    }

    private Vector<Field> fieldsFrom(Collection<?> collection) {
        if (collection.isEmpty())
            return Vector.empty();
        else
            return Vector
                    .of(collection.iterator().next())
                    .filter(Objects::nonNull)
                    .map(Object::getClass)
                    .flatMap(clazz -> List.of(clazz.getDeclaredFields()));
    }

    private boolean isSimple(Field f) {
        Class clazz = f.getType();
        return clazz.isPrimitive() ||
                clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Long.class) ||
                clazz.isAssignableFrom(Double.class) ||
                clazz.isAssignableFrom(Integer.class) ||
                clazz.isAssignableFrom(Boolean.class);
    }

    private String name(Field field) {
        return getAnnotation(field, ExcelSimpleField.class)
                .map(ExcelSimpleField::label)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(field.getName());
    }
}