package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.annotations.Inline;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.*;
import static io.vavr.collection.Vector.ofAll;

@AllArgsConstructor
class SimpleFieldExtractor {

    List<SimpleField> extract(Object object) {
        return ofAll(fieldsFrom(object))
                .flatMap(field -> sampleFields(field, object))
                .toJavaList();
    }

    @SneakyThrows
    private List<SimpleField> sampleFields(Field field, Object object) {
        field.setAccessible(true);
        if (isSimple(field))
            return List.of(new SimpleField(nameOf(field), field.get(object).toString()));
        else if (field.isAnnotationPresent(Inline.class)) {
            return extract(field.get(object));
        } else
            return List.of();
    }
}