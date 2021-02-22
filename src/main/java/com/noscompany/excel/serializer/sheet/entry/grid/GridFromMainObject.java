package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.commons.ExcelUtils;
import io.vavr.control.Option;

import java.util.List;

class GridFromMainObject {
    private final Config config;
    private final LinesCreator linesCreator;

    public GridFromMainObject(Config config) {
        this.config = config;
        linesCreator = new LinesCreator(config);
    }

    Grid create(Object object) {
        Option<Grid.Label> label = label(object);
        List<Object> arg = object == null ? List.of() : List.of(object);
        Lines lines = linesCreator.create(arg);
        return new Grid(label, lines);
    }

    private Option<Grid.Label> label(Object object) {
        String name = ExcelUtils.nameOf(object.getClass());
        Option<Grid.Label> label;
        if (config.isLabels())
            label = Option.of(new Grid.Label(name, config.getLabelColor()));
        else
            label = Option.none();
        return label;
    }

}
