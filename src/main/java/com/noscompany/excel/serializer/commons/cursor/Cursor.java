package com.noscompany.excel.serializer.commons.cursor;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.Size;
import lombok.AllArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
public abstract class Cursor {
    protected CellAddress currentPosition;
    protected int implicitOffset;

    public CellAddress position() {
        return currentPosition;
    }

    public abstract void moveBy(int offset);

    public abstract void moveBy(Size size);

    public abstract void move();

    public static Cursor vertical(CellAddress startingPosition, int implicitOffset) {
        return new VerticalCursor(startingPosition, implicitOffset);
    }

    public static Cursor horizontal(CellAddress startingPosition, int implicitOffset) {
        return new HorizontalCursor(startingPosition, implicitOffset);
    }
}
