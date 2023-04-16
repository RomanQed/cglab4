package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface PointDrawer {
    void draw(double x, double y, Color color);
}
