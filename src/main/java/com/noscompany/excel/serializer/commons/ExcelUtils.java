package com.noscompany.excel.serializer.commons;

import com.noscompany.excel.serializer.annotations.ClassName;
import com.noscompany.excel.serializer.annotations.FieldName;
import io.vavr.control.Option;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ExcelUtils {
    public static List<Field> fieldsFrom(Object object) {
        if (object == null)
            return List.of();
        else
            return Arrays.asList(object.getClass().getDeclaredFields());
    }

    public static <T extends Annotation> Option<T> getAnnotation(Class<?> clazz, Class<T> annotation) {
        if (clazz.isAnnotationPresent(annotation))
            return Option.of(clazz.getAnnotation(annotation));
        else
            return Option.none();
    }

    public static <T extends Annotation> Option<T> getAnnotation(Field f, Class<T> annotationClass) {
        if (f.isAnnotationPresent(annotationClass))
            return Option.of(f.getAnnotation(annotationClass));
        else
            return Option.none();
    }

    public static boolean isComplex(Class<?> clazz) {
        return !isSimple(clazz) && !isCollection(clazz);
    }

    public static boolean isSimple(Class<?> clazz) {
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static boolean isSimple(Field field) {
        return isSimple(field.getType());
    }

    private static boolean isPrimitiveWrapper(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Long.class) ||
                clazz.isAssignableFrom(Double.class) ||
                clazz.isAssignableFrom(Integer.class) ||
                clazz.isAssignableFrom(Boolean.class) ||
                clazz.isAssignableFrom(Short.class) ||
                clazz.isAssignableFrom(Byte.class) ||
                clazz.isAssignableFrom(Character.class);
    }

    public static boolean isCollection(Field field) {
        return isCollection(field.getType());
    }

    public static boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    public static String nameOf(Field field) {
        return getAnnotation(field, FieldName.class)
                .map(FieldName::Value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(field.getName());
    }

    public static String nameOf(Class<?> clazz) {
        return getAnnotation(clazz, ClassName.class)
                .map(ClassName::value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(clazz.getSimpleName());
    }
}