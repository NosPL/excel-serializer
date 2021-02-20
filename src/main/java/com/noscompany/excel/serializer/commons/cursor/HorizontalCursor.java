package com.noscompany.excel.serializer.commons.cursor;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.Size;

class HorizontalCursor extends Cursor {

    HorizontalCursor(CellAddress currentPosition, int implicitOffset) {
        super(currentPosition, implicitOffset);
    }

    @Override
    public void moveBy(int offset) {
        currentPosition = currentPosition.moveToRight(offset + implicitOffset);
    }

    @Override
    public void moveBy(Size size) {
        currentPosition = currentPosition.moveToRight(size.getWidth() + implicitOffset);
    }

    @Override
    public void move() {
        currentPosition = currentPosition.moveToRight(implicitOffset);
    }
}
