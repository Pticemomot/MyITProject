package com.vilorg.mydraw;

import android.graphics.Path;

public class FingerPath {
    public int color;
    public int currentPaintSize;
    public Path path;

    public FingerPath(int color, int currentPaintSize, Path path) {
        this.color = color;
        this.currentPaintSize = currentPaintSize;
        this.path = path;
    }
}
