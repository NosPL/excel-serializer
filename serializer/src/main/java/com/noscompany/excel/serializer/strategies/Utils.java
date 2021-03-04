package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.commons.JessyObject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

class Utils {
    static boolean isFlat(Iterable<? extends JessyObject> iterable) {
        if (iterable.iterator().hasNext()) {
            JessyObject next = iterable.iterator().next();
            return !hasCollectionFields(next);
        } else
            return false;
    }

    private static boolean hasCollectionFields(JessyObject next) {
        return List
                .of(next.getClass().getDeclaredFields())
                .stream()
                .map(Field::getType)
                .anyMatch(Collection.class::isAssignableFrom);
    }
}
