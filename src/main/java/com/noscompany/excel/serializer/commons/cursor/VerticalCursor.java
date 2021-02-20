package com.noscompany.excel.serializer.commons.cursor;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.Size;

class VerticalCursor extends Cursor {
    VerticalCursor(CellAddress currentPosition, int implicitOffset) {
        super(currentPosition, implicitOffset);
    }

    @Override
    public void moveBy(int offset) {
        currentPosition = currentPosition.moveDown(offset + implicitOffset);
    }

    @Override
    public void moveBy(Size size) {
        currentPosition = currentPosition.moveDown(size.getHeight() + implicitOffset);
    }

    @Override
    public void move() {
        currentPosition = currentPosition.moveDown(implicitOffset);
    }
}
