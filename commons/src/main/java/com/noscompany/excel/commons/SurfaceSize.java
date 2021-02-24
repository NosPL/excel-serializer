package com.noscompany.excel.commons;

import lombok.Value;

@Value
public class SurfaceSize {
    int width;
    int height;

    public SurfaceSize addWidth(int width) {
        return new SurfaceSize(this.width + width, height);
    }

    public SurfaceSize addHeight(int height) {
        return new SurfaceSize(this.width, this.height + height);
    }

}