package com.noscompany.excel.serializer.commons;

import lombok.Value;

@Value
public class Size {
    int width;
    int height;

    public Size addWidth(int width) {
        return new Size(this.width + width, height);
    }

    public Size addHeight(int height) {
        return new Size(this.width, this.height + height);
    }

    public int squareArea() {
        return width * height;
    }
}
