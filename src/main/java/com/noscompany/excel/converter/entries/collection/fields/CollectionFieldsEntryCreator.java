package com.noscompany.excel.converter.entries.collection.fields;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.ExcelUtils;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.noscompany.excel.converter.ExcelUtils.isSimple;
import static com.noscompany.excel.converter.ExcelUtils.simpleFieldCountOfTypeInsideCollection;
import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;

public class CollectionFieldsEntryCreator {
    private final Config config;
    private final SimpleCollectionEntryCreator simpleCollectionEntryCreator;
    private final ComplexCollectionEntryCreator complexCollectionEntryCreator;

    public CollectionFieldsEntryCreator(Config config) {
        this.config = config;
        this.simpleCollectionEntryCreator = new SimpleCollectionEntryCreator();
        this.complexCollectionEntryCreator = new ComplexCollectionEntryCreator();
    }

    @SneakyThrows
    public <T> Set<CellEntry> cellEntries(T parent, CellAddress startingPoint) {
        CellAddress currentPoint = updatePosition(startingPoint);
        List<CollectionField<T>> collectionFields = ExcelUtils.collectionFields(parent);
        HashSet<CellEntry> result = new HashSet<>();
        for (CollectionField<T> f : collectionFields) {
            Collection<?> collection = f.getValue();
            if (collection.isEmpty())
                continue;
            if (config.isCollectionLabels()) {
                result.add(new CellEntry(currentPoint, f.getTitle()));
                currentPoint = currentPoint.moveDown(1);
            }
            result.addAll(getEntries(currentPoint, collection));
            currentPoint = currentPoint.moveDown(height(collection) + config.getSpaceBetweenCollections());
        }
        return result;
    }

    private int height(Collection<?> collection) {
        if (isSimple(collection))
            return 1;
        return simpleFieldCountOfTypeInsideCollection(collection);
    }

    private Set<CellEntry> getEntries(CellAddress currentPoint, Collection<?> collection) {
        if (ExcelUtils.isSimple(collection))
            return simpleCollectionEntryCreator.getEntries(currentPoint, collection);
        else if (ExcelUtils.isComplex(collection))
            return complexCollectionEntryCreator.getEntries(currentPoint, collection);
        else
            return Set.of();
    }

    private CellAddress updatePosition(CellAddress startingPoint) {
        return config.getSimpleFieldsOrientation() == VERTICAL ? startingPoint.moveToRight(2 + config.getSpaceBetweenSimpleFieldsAndCollections()) : startingPoint.moveDown(config.mainObjectLabelOffset() + 2 + config.getSpaceBetweenSimpleFieldsAndCollections());
    }
}
