package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.sheet.entry.schema.creator.SchemaUtils;

class Utils {
    static boolean isFlat(Iterable<? extends JessyObject> iterable) {
        if (iterable.iterator().hasNext()) {
            JessyObject next = iterable.iterator().next();
            return !hasCollectionFields(next);
        } else
            return false;
    }

    private static boolean hasCollectionFields(JessyObject next) {
        return SchemaUtils
                .fieldsFromClass(next.getClass())
                .stream()
                .anyMatch(SchemaUtils::isCollection);
    }
}
