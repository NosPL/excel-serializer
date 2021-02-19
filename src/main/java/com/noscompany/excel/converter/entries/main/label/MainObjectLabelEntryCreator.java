package com.noscompany.excel.converter.entries.main.label;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.annotations.ExcelLabel;
import io.vavr.control.Option;

public class MainObjectLabelEntryCreator {
    private final Config config;

    public MainObjectLabelEntryCreator(Config config) {
        this.config = config;
    }

    public <T> Option<CellEntry> create(T parent, CellAddress cellAddress) {
        if (config.isObjectLabel()) {
            String label = getObjectLabel(parent);
            return Option.of(new CellEntry(cellAddress, label));
        } else
            return Option.none();
    }

    private <T> String getObjectLabel(T parent) {
        if (parent.getClass().isAnnotationPresent(ExcelLabel.class)) {
            String value = parent.getClass().getAnnotation(ExcelLabel.class).value();
            if (value.isEmpty())
                return parent.getClass().getSimpleName();
            else
                return value;
        } else
            return parent.getClass().getSimpleName();
    }
}
