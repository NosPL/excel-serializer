package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.annotations.Inline;
import com.noscompany.excel.serializer.commons.ExcelUtils;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.fieldsFrom;
import static com.noscompany.excel.serializer.commons.ExcelUtils.nameOf;
import static io.vavr.collection.Vector.ofAll;


class ComplexFieldExtractor {

    List<ComplexField> extract(Object object) {
        return ofAll(fieldsFrom(object))
                .filter(this::isComplexAndNotInlined)
                .map(field -> complexField(field, object))
                .toJavaList();
    }

    private boolean isComplexAndNotInlined(Field field) {
        return ExcelUtils.isComplex(field.getType()) && !field.isAnnotationPresent(Inline.class);
    }

    @SneakyThrows
    private ComplexField complexField(Field field, Object object) {
        field.setAccessible(true);
        return new ComplexField(nameOf(field), field.get(object));
    }
}
