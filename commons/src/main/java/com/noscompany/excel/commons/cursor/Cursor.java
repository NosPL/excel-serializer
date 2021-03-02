package com.noscompany.excel.commons.cursor;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.SurfaceSize;
import lombok.AllArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
public abstract class Cursor {
    protected CellAddress currentPosition;
    protected int implicitOffset;

    public CellAddress position() {
        return currentPosition;
    }

    public abstract void moveBy(SurfaceSize surfaceSize);

    public abstract void move();

    public static Cursor vertical(CellAddress startingPosition, int implicitOffset) {
        return new VerticalCursor(startingPosition, implicitOffset);
    }

    public static Cursor horizontal(CellAddress startingPosition, int implicitOffset) {
        return new HorizontalCursor(startingPosition, implicitOffset);
    }
}
