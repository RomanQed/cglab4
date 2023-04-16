package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("cp")
public class ParametricCircleDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        // Также, как и в каноническом способе, достаточно отрисовать лишь сектор окружности и затем дублировать его
        // Однако в этом случае нужно вычислять не границу, а шаг, с которым будет увеличиваться параметр
        double step = 1 / ellipse.a;
        double i = 0;
        Point center = ellipse.center;
        // PI/4 - 1/8 окружности, всё остальное можно получить симметричными отражениями
        while (i <= Math.PI / 4 + step) {
            double x = center.x + ellipse.a * Math.cos(i);
            double y = center.y + ellipse.a * Math.sin(i);
            DrawUtil.drawSymmetricalCircle(new Point(x, y), center, color, drawer);
            i += step;
        }
    }
}
