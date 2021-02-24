package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.annotations.Inline;
import com.noscompany.excel.sheet.entry.schema.ComplexType;
import com.noscompany.excel.sheet.entry.schema.SimpleType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

import static com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils.fieldsFromObject;
import static com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils.nameOf;
import static io.vavr.collection.Vector.ofAll;


class ComplexTypeExtractor {
    private final SimpleTypeExtractor simpleTypeExtractor = new SimpleTypeExtractor();

    List<ComplexType> extract(Object object) {
        return ofAll(fieldsFromObject(object))
                .filter(this::isComplexAndNotInlined)
                .map(field -> create(field, object))
                .toJavaList();
    }

    private boolean isComplexAndNotInlined(Field field) {
        return SchemaUtils.isComplex(field.getType()) && !field.isAnnotationPresent(Inline.class);
    }

    @SneakyThrows
    private ComplexType create(Field field, Object object) {
        field.setAccessible(true);
        Object o = field.get(object);
        List<SimpleType> simpleTypes = simpleTypeExtractor.extract(o);
        return new ComplexType(nameOf(field), simpleTypes);
    }
}
