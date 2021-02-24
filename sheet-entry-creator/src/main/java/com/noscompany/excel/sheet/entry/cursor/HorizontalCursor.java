package com.noscompany.excel.sheet.entry.cursor;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.SurfaceSize;

class HorizontalCursor extends Cursor {

    HorizontalCursor(CellAddress currentPosition, int implicitOffset) {
        super(currentPosition, implicitOffset);
    }

    @Override
    public void moveBy(SurfaceSize surfaceSize) {
        currentPosition = currentPosition.moveToRight(surfaceSize.getWidth() + implicitOffset);
    }

    @Override
    public void move() {
        currentPosition = currentPosition.moveToRight(implicitOffset);
    }
}
