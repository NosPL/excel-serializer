package com.noscompany.excel.commons.schema.creator;

import com.noscompany.excel.commons.schema.ValueCollection;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static io.vavr.control.Option.none;
import static java.util.stream.Collectors.toList;

class SimpleCollectionExtractor {

    List<ValueCollection> extract(Object object) {
        return Vector.ofAll(SchemaUtils.fieldsFromObject(object))
                .filter(SchemaUtils::isCollection)
                .flatMap(f -> create(object, f))
                .toJavaList();
    }

    @SneakyThrows
    private Option<ValueCollection> create(Object object, Field field) {
        field.setAccessible(true);
        Collection<?> collection = (Collection<?>) field.get(object);
        if (collection == null || collection.isEmpty())
            return none();
        List<String> values;
        Object next = collection.iterator().next();
        if (SchemaUtils.isSimple(next.getClass())) {
            values = collection.stream().map(Object::toString).collect(toList());
            return Option.of(new SimpleCollection(SchemaUtils.nameOf(field), values));
        } else {
            return Option.none();
        }
    }

}
