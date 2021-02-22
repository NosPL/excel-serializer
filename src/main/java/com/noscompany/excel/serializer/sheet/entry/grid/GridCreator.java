package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.CollectionField;
import com.noscompany.excel.serializer.field.extractor.ComplexField;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;

import java.util.List;

public class GridCreator {
    private final FieldExtractor fieldExtractor;
    private final GridFromMainObject gridFromMainObject;
    private final GridFromCollectionFields gridFromCollectionFields;
    private final GridFromComplexFields gridFromComplexFields;

    public GridCreator(Config config) {
        fieldExtractor = new FieldExtractor();
        gridFromMainObject = new GridFromMainObject(config);
        gridFromCollectionFields = new GridFromCollectionFields(config);
        gridFromComplexFields = new GridFromComplexFields(config);
    }

    public GridList create(Object object) {
        return new GridList(
                gridFromMain(object),
                grids(fromComplexFieldsInside(object)),
                gridsFrom(collectionFieldsInside(object)));
    }

    private Grid gridFromMain(Object object) {
        return gridFromMainObject.create(object);
    }

    private List<Grid> grids(List<ComplexField> complexFieldsInside) {
        return gridFromComplexFields.create(complexFieldsInside);
    }

    private List<Grid> gridsFrom(List<CollectionField> collectionFieldsInside) {
        return gridFromCollectionFields.create(collectionFieldsInside);
    }

    private List<ComplexField> fromComplexFieldsInside(Object object) {
        return fieldExtractor.complexFields(object);
    }

    private List<CollectionField> collectionFieldsInside(Object object) {
        return fieldExtractor.collectionFields(object);
    }
}