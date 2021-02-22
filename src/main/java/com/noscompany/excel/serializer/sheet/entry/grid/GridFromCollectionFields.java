package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.CollectionField;
import io.vavr.control.Option;

import java.util.List;

import static java.util.stream.Collectors.toList;

class GridFromCollectionFields {
    private final Config config;
    private final LinesCreator linesCreator;

    public GridFromCollectionFields(Config config) {
        this.config = config;
        linesCreator = new LinesCreator(config);
    }

    List<Grid> create(List<CollectionField> collectionFields) {
        return collectionFields.stream()
                .map(this::toGrid)
                .collect(toList());
    }

    private Grid toGrid(CollectionField cf) {
        Lines lines = linesCreator.create(cf.getCollection());
        return new Grid(label(cf), lines);
    }

    private Option<Grid.Label> label(CollectionField cf) {
        if (config.isLabels())
            return Option.of(new Grid.Label(cf.getName(), config.getLabelColor()));
        else
            return Option.none();
    }
}
