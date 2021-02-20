package com.noscompany.excel.serializer.commons;

import io.vavr.control.Option;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

public class ExcelUtils {

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

    public static boolean isComplex(Class clazz) {
        return !isSimple(clazz) && !isCollection(clazz);
    }

    public static boolean isSimple(Class clazz) {
        return clazz.isPrimitive() ||
                clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Long.class) ||
                clazz.isAssignableFrom(Double.class) ||
                clazz.isAssignableFrom(Integer.class) ||
                clazz.isAssignableFrom(Boolean.class);
    }

    public static boolean objectsInsideAreSimple(Collection<?> collection) {
        if (collection.isEmpty())
            return false;
        Object next = collection.iterator().next();
        return isSimple(next.getClass());
    }

    public static boolean isCollection(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }
}