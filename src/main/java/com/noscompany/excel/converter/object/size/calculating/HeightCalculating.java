package com.noscompany.excel.converter.object.size.calculating;

import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.ExcelUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;
import static com.noscompany.excel.converter.ExcelUtils.*;
import static java.lang.Math.max;

@AllArgsConstructor
public class HeightCalculating {
    private final Config config;

    @SneakyThrows
    public <T> int calculate(T object) {
        int titleOffset = config.mainObjectLabelOffset();
        int simpleFieldCount = ExcelUtils.simpleFields(object).size();
        int result;//simple field name and simple field value
        if (config.getSimpleFieldsOrientation() == VERTICAL) {
            result = max(
                    simpleFieldCount + titleOffset,
                    collectionsHeight(object)
            ) + config.getSpaceBetweenEntries();
        } else {
            result = titleOffset +
                    2 + //simple field name and simple field value
                    config.getSpaceBetweenSimpleFieldsAndCollections() +
                    collectionsHeight(object) +
                    config.getSpaceBetweenEntries();
        }
        return
                result;
    }

    public <T> int collectionsHeight(T object) {
        List<Collection> nonEmptyCollections = ExcelUtils.nonEmptyCollections(object);
        if (nonEmptyCollections.isEmpty())
            return 0;
        int collectionsFieldsAmount = 0;
        for (Collection c : nonEmptyCollections) {
            if (isSimple(c)) {
                collectionsFieldsAmount += 1;
            } else {
                Object next = c.iterator().next();
                collectionsFieldsAmount += simpleFields(next).size();
            }
        }
        return collectionsFieldsAmount + collectionLabelsCount(object) + spaceBetweenCollections(object);
    }

    private <T> int collectionLabelsCount(T object) {
        if (config.isCollectionLabels()) {
            return ExcelUtils.collectionFields(object).size();
        } else
            return 0;
    }

    private <T> int spaceBetweenCollections(T object) {
        if (nonEmptyCollections(object).isEmpty())
            return 0;
        return config.getSpaceBetweenCollections() *
                ExcelUtils.nonEmptyCollections(object).size() -
                config.getSpaceBetweenCollections();
    }
}