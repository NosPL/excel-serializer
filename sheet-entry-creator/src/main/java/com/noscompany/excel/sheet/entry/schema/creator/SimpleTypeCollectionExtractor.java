package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import io.vavr.control.Option;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils.*;
import static io.vavr.collection.Vector.ofAll;
import static io.vavr.control.Option.none;
import static java.util.stream.Collectors.toList;

class SimpleTypeCollectionExtractor {

    List<TypeCollection> extract(Object object) {
        return ofAll(fieldsFromObject(object))
                .filter(SchemaUtils::isCollection)
                .flatMap(f -> create(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private Option<TypeCollection> create(Object object, Field field) {
        field.setAccessible(true);
        Collection<?> collection = (Collection<?>) field.get(object);
        if (collection == null || collection.isEmpty())
            return none();
        List<String> values;
        Object next = collection.iterator().next();
        if (isSimple(next.getClass())) {
            values = collection.stream().map(Object::toString).collect(toList());
            return Option.of(new SimpleTypeCollection(nameOf(field), values));
        } else {
            return Option.none();
        }
    }

}
