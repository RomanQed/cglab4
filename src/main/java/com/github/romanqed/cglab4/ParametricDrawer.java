package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

@Drawer("p")
public class ParametricDrawer implements EllipseDrawer {

    @Override
    public void draw(Ellipse ellipse, Color color, PointDrawer drawer) {
        // Также, как и в каноническом способе, достаточно отрисовать лишь сектор эллипса и затем дублировать его
        // Однако в этом случае нужно вычислять не границу, а шаг, с которым будет увеличиваться параметр
        Point center = ellipse.center;
        double step = 1 / Math.max(ellipse.a, ellipse.b);
        double i = 0;
        // PI/2 - 1/4 эллипса, т.к. из-за разных радиусов 1/8 симметрично отразить не получится
        while (i <= Math.PI / 2 + step) {
            double x = center.x + Math.round(ellipse.a * Math.cos(i));
            double y = center.y + Math.round(ellipse.b * Math.sin(i));
            DrawUtil.drawSymmetrical(new Point(x, y), center, color, drawer);
            i += step;
        }
    }
}
