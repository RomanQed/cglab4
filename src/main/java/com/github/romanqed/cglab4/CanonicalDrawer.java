package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("cl")
public class CanonicalDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        double sqrA = ellipse.a * ellipse.a;
        double sqrB = ellipse.b * ellipse.b;
        Point center = ellipse.center;
        // Вычисление границ сектора эллипса, который будет симметрично дублироваться
        long borderX = Math.round(center.x + ellipse.a / Math.sqrt(1 + sqrB / sqrA));
        long borderY = Math.round(center.y + ellipse.b / Math.sqrt(1 + sqrA / sqrB));
        // В отличие от окружности, необходимо сделать 2 прохода, т.к. эллипс имеет 2 разных радиуса
        // Фактически отрисовываются 2 сектора разных радиусов и стыкуются вместе
        for (long i = Math.round(center.x); i < borderX + 1; ++i) {
            double y = center.y + Math.sqrt(sqrA * sqrB - (i - center.x) * (i - center.x) * sqrB) / ellipse.a;
            DrawUtil.drawSymmetrical(new Point(i, y), center, color, drawer);
        }
        for (long i = borderY; i > Math.round(center.y) - 1; --i) {
            double x = center.x + Math.sqrt(sqrA * sqrB - (i - center.y) * (i - center.y) * sqrA) / ellipse.b;
            DrawUtil.drawSymmetrical(new Point(x, i), center, color, drawer);
        }
    }
}
