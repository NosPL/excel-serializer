package com.noscompany.excel.converter.object.size.calculating;

import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.ExcelUtils;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;
import static com.noscompany.excel.converter.ExcelUtils.isComplex;
import static com.noscompany.excel.converter.ExcelUtils.isSimple;
import static java.lang.Math.max;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class WidthCalculating {
    private final Config config;

    public <T> int calculate(T object) {
        if (config.getSimpleFieldsOrientation() == VERTICAL) {
            return 2 + //for simple field name and simple field value
                    collectionWidth(object) +
                    nextEntryOffset();
        } else {
            int simpleFieldCount = ExcelUtils.simpleFields(object).size();
            return max(simpleFieldCount, collectionWidth(object)) + nextEntryOffset();
        }
    }

    private <T> int collectionWidth(T object) {
        if (allCollectionsInsideObjectAreEmpty(object))
            return 0;
        else
            return biggestCollectionWidth(object) +
                    config.getSpaceBetweenSimpleFieldsAndCollections();
    }

    private int nextEntryOffset() {
        if (config.getMainObjectWritingDirection() == Config.MainObjectWritingDirection.LEFT_TO_RIGHT)
            return config.getSpaceBetweenEntries();
        else
            return 0;
    }

    private <T> boolean allCollectionsInsideObjectAreEmpty(T object) {
        return getNonEmptyCollections(object).isEmpty();
    }

    private <T> List<Collection> getNonEmptyCollections(T object) {
        return ExcelUtils.
                getAllCollections(object)
                .stream()
                .filter(collection -> !collection.isEmpty())
                .collect(toList());
    }

    private <T> int biggestCollectionWidth(T object) {
        return ExcelUtils
                .getAllCollections(object)
                .stream()
                .mapToInt(this::getCollectionWidth)
                .max()
                .orElse(0);
    }

    private int getCollectionWidth(Collection collection) {
        if (isSimple(collection))
            return collection.size();
        else if (isComplex(collection))
            return collection.size() + 1; //one for field names
        else
            return 0;
    }
}
