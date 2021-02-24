package com.noscompany.excel.sheet.entry.cursor;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.SurfaceSize;

class VerticalCursor extends Cursor {
    VerticalCursor(CellAddress currentPosition, int implicitOffset) {
        super(currentPosition, implicitOffset);
    }

    @Override
    public void moveBy(SurfaceSize surfaceSize) {
        currentPosition = currentPosition.moveDown(surfaceSize.getHeight() + implicitOffset);
    }

    @Override
    public void move() {
        currentPosition = currentPosition.moveDown(implicitOffset);
    }
}
