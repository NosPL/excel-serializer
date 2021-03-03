package com.noscompany.excel.commons;

import io.vavr.control.Option;
import lombok.Value;

import java.awt.*;
import java.util.List;

@Value
public class SheetEntry {
    SurfaceSize surfaceSize;
    List<CellEntry> cellEntries;
    Option<Background> background;


    public SurfaceSize getSurfaceSize() {
        return background
                .map(Background::getPadding)
                .map(this::getSurfaceSizeWithPadding)
                .getOrElse(surfaceSize);
    }

    private SurfaceSize getSurfaceSizeWithPadding(Integer padding) {
        SurfaceSize surfaceSize = this.surfaceSize.addWidth(padding * 2).addHeight(padding * 2);
        return surfaceSize;
    }

    @Value
    public static class Background {
        int padding;
        Color color;
        CellAddress startingPoint;
    }
}