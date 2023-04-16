package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface EllipseDrawer {
    void draw(Ellipse ellipse, Color color, PointDrawer drawer);
}
