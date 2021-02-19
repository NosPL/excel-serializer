package com.noscompany.excel.converter;

import com.noscompany.excel.converter.object.size.calculating.HeightCalculating;
import com.noscompany.excel.converter.object.size.calculating.WidthCalculating;

public class Cursor {
    private final HeightCalculating heightCalculating;
    private final WidthCalculating widthCalculating;
    private final Config config;
    private CellAddress currentPosition;

    public Cursor(Config config) {
        heightCalculating = new HeightCalculating(config);
        widthCalculating = new WidthCalculating(config);
        this.config = config;
        currentPosition = config.getStartingPosition();
    }

    public <T> void moveToNextStartingPoint(T object) {
        if (config.getMainObjectWritingDirection() == Config.MainObjectWritingDirection.TOP_TO_BOTTOM)
            currentPosition = currentPosition.moveDown(heightCalculating.calculate(object));
        else
            currentPosition = currentPosition.moveToRight(widthCalculating.calculate(object));
    }

    public CellAddress getCurrentPosition() {
        return currentPosition;
    }
}