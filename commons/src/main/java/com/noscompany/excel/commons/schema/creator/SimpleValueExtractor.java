package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.annotations.Inline;
import com.noscompany.excel.commons.schema.SimpleValue;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
class SimpleValueExtractor {

    List<SimpleValue> extract(Object object) {
        return Vector.ofAll(SchemaUtils.fieldsFromObject(object))
                .flatMap(field -> simpleFields(field, object))
                .toJavaList();
    }

    @SneakyThrows
    private List<SimpleValue> simpleFields(Field field, Object object) {
        field.setAccessible(true);
        if (SchemaUtils.isSimple(field))
            return List.of(new SimpleValue(SchemaUtils.nameOf(field), field.get(object).toString()));
        else if (field.isAnnotationPresent(Inline.class)) {
            return extract(field.get(object));
        } else
            return List.of();
    }
}