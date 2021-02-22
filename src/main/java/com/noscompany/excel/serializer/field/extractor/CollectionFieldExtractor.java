package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.commons.ExcelUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.*;
import static io.vavr.collection.Vector.ofAll;

class CollectionFieldExtractor {

    List<CollectionField> extract(Object object) {
        return ofAll(fieldsFrom(object))
                .filter(ExcelUtils::isCollection)
                .map(f -> createCollectionField(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private CollectionField createCollectionField(Object object, Field field) {
        field.setAccessible(true);
        Collection<?> collection = (Collection<?>) field.get(object);
        if (collection == null)
            collection = List.of();
        return new CollectionField(nameOf(field), collection);
    }
}
