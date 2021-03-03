package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.commons.annotations.FieldName;
import io.vavr.control.Option;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SchemaUtils {
    static List<Field> fieldsFromObject(Object object) {
        if (object == null)
            return List.of();
        else
            return fieldsFromClass(object.getClass());
    }

    public static List<Field> fieldsFromClass(Class<?> aClass) {
        return Arrays.asList(aClass.getDeclaredFields());
    }

    static <T extends Annotation> Option<T> getAnnotation(Class<?> clazz, Class<T> annotation) {
        if (clazz.isAnnotationPresent(annotation))
            return Option.of(clazz.getAnnotation(annotation));
        else
            return Option.none();
    }

    static <T extends Annotation> Option<T> getAnnotation(Field f, Class<T> annotationClass) {
        if (f.isAnnotationPresent(annotationClass))
            return Option.of(f.getAnnotation(annotationClass));
        else
            return Option.none();
    }

    static boolean isComplex(Class<?> clazz) {
        return !isSimple(clazz) && !isCollection(clazz);
    }

    static boolean isComplex(Field field) {
        return isComplex(field.getType());
    }

    static boolean isSimple(Class<?> clazz) {
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    static boolean isSimple(Field field) {
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

    static boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    static String nameOf(Field field) {
        return getAnnotation(field, FieldName.class)
                .map(FieldName::Value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(field.getName());
    }

    static String nameOf(Class<?> clazz) {
        return getAnnotation(clazz, ClassName.class)
                .map(ClassName::value)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .getOrElse(clazz.getSimpleName());
    }
}