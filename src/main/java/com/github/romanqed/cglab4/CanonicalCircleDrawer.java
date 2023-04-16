package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("ccl")
public class CanonicalCircleDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double sqrR = ellipse.a * ellipse.a;
        Point center = ellipse.center;
        // Граница сектора окружности, который будет симметрично дублироваться
        long border = Math.round(center.x + ellipse.a / Math.sqrt(2));
        for (double x = center.x; x < border + 1; ++x) {
            // Вычисление y
            double y = center.y + Math.sqrt(sqrR - (x - center.x) * (x - center.x));
            // Отрисовка симметрично расположенных пикселей
            DrawUtil.drawSymmetricalCircle(new Point(x, y), center, color, drawer);
        }
    }
}
