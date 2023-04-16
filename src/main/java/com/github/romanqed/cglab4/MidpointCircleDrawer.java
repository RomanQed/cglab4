package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("cmp")
public class MidpointCircleDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double x = ellipse.a;
        double y = 0;
        Point center = ellipse.center;
        DrawUtil.drawSymmetricalCircle(new Point(x + center.x, y + center.y), center, color, drawer);
        double delta = 1 - ellipse.a;
        while (x >= y) {
            if (delta >= 0) {
                --x;
                ++y;
                delta += 2 * y + 1 - 2 * x;
            } else {
                ++y;
                delta += 2 * y + 1;
            }
            DrawUtil.drawSymmetricalCircle(new Point(x + center.x, y + center.y), center, color, drawer);
        }
    }
}
