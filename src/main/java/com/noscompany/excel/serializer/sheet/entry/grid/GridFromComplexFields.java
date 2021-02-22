package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.ComplexField;
import io.vavr.control.Option;

import java.util.List;

import static java.util.stream.Collectors.toList;

class GridFromComplexFields {
    private final Config config;
    private final LinesCreator linesCreator;

    GridFromComplexFields(Config config) {
        this.config = config;
        linesCreator = new LinesCreator(config);
    }

    List<Grid> create(List<ComplexField> complexFields) {
        return complexFields.stream()
                .map(complexField -> createGrid(complexField.getObject(), label(complexField)))
                .collect(toList());
    }

    private Grid createGrid(Object object, Option<Grid.Label> label) {
        List<Object> arg = object == null ? List.of() : List.of(object);
        Lines lines = linesCreator.create(arg);
        return new Grid(label, lines);
    }

    private Option<Grid.Label> label(ComplexField c) {
        return config.isLabels() ? Option.of(new Grid.Label(c.getLabel(), config.getLabelColor())) : Option.none();
    }
}
