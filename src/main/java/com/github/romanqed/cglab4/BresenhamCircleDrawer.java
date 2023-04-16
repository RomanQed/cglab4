package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("cbr")
public class BresenhamCircleDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double x = 0;
        double y = ellipse.a;
        Point center = ellipse.center;
        DrawUtil.drawSymmetricalCircle(new Point(x + center.x, y + center.y), center, color, drawer);
        double delta = 2 * (1 - ellipse.a);
        while (y >= x) {
            double d = 2 * (delta + y) - 1;
            ++x;
            if (d >= 0) {
                --y;
                delta += 2 * (x - y + 1);
            } else {
                delta += x + x + 1;
            }
            DrawUtil.drawSymmetricalCircle(new Point(x + center.x, y + center.y), center, color, drawer);
        }
    }
}
