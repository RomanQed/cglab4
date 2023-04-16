package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("br")
public class BresenhamDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double x = 0;
        double y = ellipse.b;
        Point toDraw = new Point(x + ellipse.center.x, y + ellipse.center.y);
        DrawUtil.drawSymmetrical(toDraw, ellipse.center, color, drawer);
        double sqrA = ellipse.a * ellipse.a;
        double sqrB = ellipse.b * ellipse.b;
        double delta = sqrB - sqrA * (2 * ellipse.b + 1);
        while (y >= 0) {
            if (delta < 0) {
                double d1 = 2 * delta + sqrA * (2 * y + 2);
                ++x;
                if (d1 < 0) {
                    delta += sqrB * (2 * x + 1);
                } else {
                    --y;
                    delta += sqrB * (2 * x + 1) + sqrA * (1 - 2 * y);
                }
            } else if (delta > 0) {
                double d2 = 2 * delta + sqrB * (2 - 2 * x);
                --y;
                if (d2 > 0) {
                    delta += sqrA * (1 - 2 * y);
                } else {
                    ++x;
                    delta += sqrB * (2 * x + 1) + sqrA * (1 - 2 * y);
                }
            } else {
                --y;
                ++x;
                delta += sqrB * (2 * x + 1) + sqrA * (1 - 2 * y);
            }
            toDraw = new Point(x + ellipse.center.x, y + ellipse.center.y);
            DrawUtil.drawSymmetrical(toDraw, ellipse.center, color, drawer);
        }
    }
}
