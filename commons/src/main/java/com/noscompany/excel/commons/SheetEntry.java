package com.noscompany.excel.commons;

import io.vavr.control.Option;
import lombok.Value;

import java.awt.*;

@Value
public class SheetEntry {
    CellEntries cellEntries;
    Option<Background> background;


    public SurfaceSize getSurfaceSize() {
        return background
                .map(Background::getPadding)
                .map(this::getSurfaceSizeWithPadding)
                .getOrElse(cellEntries.getSurfaceSize());
    }

    private SurfaceSize getSurfaceSizeWithPadding(Integer padding) {
        return cellEntries
                .getSurfaceSize()
                .addWidth(padding * 2)
                .addHeight(padding * 2);
    }

    @Value
    public static class Background {
        int padding;
        Color color;
        CellAddress startingPoint;
    }
}