package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("mp")
public class MidpointDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double sqrA = ellipse.a * ellipse.a;
        double sqrB = ellipse.b * ellipse.b;
        double x = 0;
        double y = ellipse.b;
        Point center = ellipse.center;
        DrawUtil.drawSymmetrical(new Point(x + center.x, y + center.y), center, color, drawer);
        long border = Math.round(ellipse.a / Math.sqrt(1 + sqrB / sqrA));
        double delta = sqrB - Math.round(sqrA * (ellipse.b - 0.25));
        while (x <= border) {
            ++x;
            if (delta < 0) {
                delta += 2 * sqrB * x + 1;
            } else {
                --y;
                delta += 2 * sqrB * x - 2 * sqrA * y + 1;
            }
            DrawUtil.drawSymmetrical(new Point(x + center.x, y + center.y), center, color, drawer);
        }
        x = ellipse.a;
        y = 0;
        DrawUtil.drawSymmetrical(new Point(x + center.x, y + center.y), center, color, drawer);
        border = Math.round(ellipse.b / Math.sqrt(1 + sqrA / sqrB));
        delta = sqrA - Math.round(sqrB * (ellipse.a - 0.25));
        while (y <= border) {
            ++y;
            if (delta < 0) {
                delta += 2 * sqrA * y + 1;
            } else {
                --x;
                delta += 2 * sqrA * y - 2 * sqrB * x + 1;
            }
            DrawUtil.drawSymmetrical(new Point(x + center.x, y + center.y), center, color, drawer);
        }
    }
}
