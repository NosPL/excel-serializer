package com.noscompany.excel.sheet.entry.tables.drawing.strategy;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.Config.BackgroundConfig;
import com.noscompany.excel.commons.SheetEntry.Background;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BackgroundCreator {
    private final Config config;

    public Option<Background> createFor(CellAddress startingPosition) {
        return config
                .getBackgroundConfig()
                .map(bc -> createBackground(bc, startingPosition));
    }

    private Background createBackground(BackgroundConfig bc, CellAddress startingPosition) {
        return new Background(
                bc.getPadding(),
                bc.getColor(),
                startingPosition);
    }
}
