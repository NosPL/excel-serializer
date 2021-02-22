package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.annotations.Inline;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.*;

@AllArgsConstructor
class SimpleFieldExtractor {

    List<SimpleField> extract(Object object) {
        return Vector
                .ofAll(fieldsFrom(object))
                .flatMap(field -> sampleFields(field, object))
                .toJavaList();
    }

    @SneakyThrows
    private List<SimpleField> sampleFields(Field field, Object object) {
        try {
            field.setAccessible(true);
            if (isSimple(field.getType()))
                return List.of(new SimpleField(name(field), field.get(object).toString()));
            else if (field.isAnnotationPresent(Inline.class)) {
                return extract(field.get(object));
            } else
                return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
}