package com.noscompany.excel.converter;

import com.noscompany.excel.converter.entries.collection.fields.CollectionField;
import com.noscompany.excel.converter.entries.simple.fields.SimpleField;
import io.vavr.collection.Vector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExcelUtils {

    public static <T> List<SimpleField<T>> simpleFields(T object) {
        java.lang.reflect.Field[] declaredFields = object.getClass().getDeclaredFields();
        return Vector.of(declaredFields)
                .flatMap(f -> SimpleField.instance(f, object))
                .toJavaList();
    }

    public static int simpleFieldCountOfTypeInsideCollection(Collection<?> collection) {
        return Vector
                .of(getTypeFromInsideCollection(collection).getDeclaredFields())
                .filter(ExcelUtils::isSimple)
                .size();
    }

    public static boolean isSimple(Field f) {
        return f.getType().isPrimitive() ||
                f.getType().isAssignableFrom(String.class) ||
                f.getType().isAssignableFrom(Long.class) ||
                f.getType().isAssignableFrom(Double.class) ||
                f.getType().isAssignableFrom(Integer.class) ||
                f.getType().isAssignableFrom(Boolean.class);
    }

    public static <T> List<CollectionField<T>> collectionFields(T o) {
        Field[] fields = o.getClass().getDeclaredFields();
        return Vector.of(fields)
                .flatMap(f -> CollectionField.instance(f, o))
                .toJavaList();
    }

    public static Class getTypeFromInsideCollection(Collection collection) {
        Object next = collection.iterator().next();
        Class<?> result = next.getClass();
        return result;
    }

    public static <T> List<Collection> getAllCollections(T object) {
        return ExcelUtils.collectionFields(object)
                .stream()
                .map(CollectionField::getValue)
                .filter(collection -> !collection.isEmpty())
                .collect(toList());
    }

    public static boolean isSimple(Collection<?> collection) {
        if (collection.isEmpty())
            return false;
        Object next = collection.iterator().next();
        return next.getClass().isPrimitive() ||
                next.getClass().isAssignableFrom(String.class) ||
                next.getClass().isAssignableFrom(Long.class) ||
                next.getClass().isAssignableFrom(Double.class) ||
                next.getClass().isAssignableFrom(Integer.class) ||
                next.getClass().isAssignableFrom(Boolean.class);
    }

    public static boolean isComplex(Collection<?> collection) {
        if (collection.isEmpty())
            return false;
        return !isSimple(collection);
    }

    public static <T> List<Collection> nonEmptyCollections(T object) {
        return getAllCollections(object)
                .stream()
                .filter(collection -> !collection.isEmpty())
                .collect(toList());
    }
}