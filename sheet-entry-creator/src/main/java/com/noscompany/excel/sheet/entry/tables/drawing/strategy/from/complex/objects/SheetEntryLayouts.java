package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.Config;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
enum SheetEntryLayouts {
    _1(SheetEntryLayout1::new),
    _2(SheetEntryLayout2::new);

    private final Function<Config, SheetEntryLayout> tableLayoutCreator;

    SheetEntryLayout get(Config config) {
        return tableLayoutCreator.apply(config);
    }
}
