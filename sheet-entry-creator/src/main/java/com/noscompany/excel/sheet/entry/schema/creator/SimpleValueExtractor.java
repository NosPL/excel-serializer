package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.annotations.Inline;
import com.noscompany.excel.sheet.entry.schema.SimpleValue;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils.*;
import static io.vavr.collection.Vector.ofAll;

@AllArgsConstructor
class SimpleValueExtractor {

    List<SimpleValue> extract(Object object) {
        return ofAll(fieldsFromObject(object))
                .flatMap(field -> simpleFields(field, object))
                .toJavaList();
    }

    @SneakyThrows
    private List<SimpleValue> simpleFields(Field field, Object object) {
        field.setAccessible(true);
        if (isSimple(field))
            return List.of(new SimpleValue(nameOf(field), field.get(object).toString()));
        else if (field.isAnnotationPresent(Inline.class)) {
            return extract(field.get(object));
        } else
            return List.of();
    }
}