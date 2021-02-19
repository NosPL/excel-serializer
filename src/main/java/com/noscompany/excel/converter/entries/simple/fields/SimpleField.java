package com.noscompany.excel.converter.entries.simple.fields;


import com.noscompany.excel.converter.annotations.ExcelField;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

import static com.noscompany.excel.converter.ExcelUtils.isSimple;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class SimpleField<T> {
    @Getter
    private final Field field;
    private final T object;

    public String getName() {
        return getAnnotationValue()
                .getOrElse(field.getName());
    }

    @SneakyThrows
    public String getValue() {
        field.setAccessible(true);
        return field.get(object).toString();
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

    public static <T> Option<SimpleField<T>> instance(Field field, T object) {
        if (isSimple(field))
            return Option.of(new SimpleField(field, object));
        else
            return Option.none();
    }
}

