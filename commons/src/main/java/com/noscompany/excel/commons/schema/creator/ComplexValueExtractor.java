package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.schema.ComplexValue;
import com.noscompany.excel.commons.schema.SimpleValue;
import io.vavr.collection.Vector;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;


class ComplexValueExtractor {
    private final SimpleValueExtractor simpleValueExtractor = new SimpleValueExtractor();

    List<ComplexValue> extract(Object object) {
        return Vector.ofAll(SchemaUtils.fieldsFromObject(object))
                .filter(SchemaUtils::isComplex)
                .map(field -> create(field, object))
                .toJavaList();
    }

    @SneakyThrows
    private ComplexValue create(Field field, Object object) {
        field.setAccessible(true);
        Object o = field.get(object);
        List<SimpleValue> simpleValues = simpleValueExtractor.extract(o);
        return new ComplexValue(SchemaUtils.nameOf(field), simpleValues);
    }
}
