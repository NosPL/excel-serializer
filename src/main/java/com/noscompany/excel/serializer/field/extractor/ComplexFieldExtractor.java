package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.annotations.Inline;
import com.noscompany.excel.serializer.commons.ExcelUtils;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.name;


class ComplexFieldExtractor {

    List<ComplexField> extract(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Vector
                .of(fields)
                .filter(this::isComplex)
                .map(field -> complexField(field, object))
                .toJavaList();
    }

    private boolean isComplex(Field field) {
        return ExcelUtils.isComplex(field.getType()) && !field.isAnnotationPresent(Inline.class);
    }

    @SneakyThrows
    private ComplexField complexField(Field field, Object object) {
        field.setAccessible(true);
        return new ComplexField(name(field), field.get(object));
    }
}
