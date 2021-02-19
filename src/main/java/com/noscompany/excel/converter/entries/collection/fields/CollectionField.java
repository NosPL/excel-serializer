package com.noscompany.excel.converter.entries.collection.fields;

import com.noscompany.excel.converter.annotations.ExcelField;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
public class CollectionField<T> {
    private final Field field;
    private final T o;

    public String getTitle() {
        return getAnnotationValue().getOrElse(field.getName());
    }

    @SneakyThrows
    public Collection getValue() {
        field.setAccessible(true);
        return (Collection) field.get(o);
    }

    private Option<String> getAnnotationValue() {
        if (field.isAnnotationPresent(ExcelField.class)) {
            String value = field.getAnnotation(ExcelField.class).value();
            if (value.isEmpty())
                return Option.none();
            else
                return Option.of(value);
        } else
            return Option.none();
    }

    public static <T> Option<CollectionField<T>> instance(Field field, T o) {
        if (Collection.class.isAssignableFrom(field.getType()))
            return Option.of(new CollectionField(field, o));
        else
            return Option.none();
    }
}