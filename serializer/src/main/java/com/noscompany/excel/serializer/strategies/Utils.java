package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils;

class Utils {
    static boolean isFlat(Iterable<?> iterable) {
        if (iterable.iterator().hasNext()) {
            Object next = iterable.iterator().next();
            return !hasCollectionFields(next);
        } else
            return false;
    }

    private static boolean hasCollectionFields(Object next) {
        return SchemaUtils
                .fieldsFromClass(next.getClass())
                .stream()
                .anyMatch(SchemaUtils::isCollection);
    }
}
