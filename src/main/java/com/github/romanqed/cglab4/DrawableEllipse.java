package com.github.romanqed.cglab4;

import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawableEllipse extends Ellipse {
    private Color color;
    private EllipseDrawer drawer;
    private PointDrawer pointDrawer;

    public DrawableEllipse(Point center, double a, double b) {
        super(center, a, b);
        this.color = Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setDrawer(EllipseDrawer drawer) {
        this.drawer = drawer;
    }

    public EllipseDrawer getDrawer() {
        return drawer;
    }

    public void setPointDrawer(PointDrawer drawer) {
        this.pointDrawer = drawer;
    }

    public PointDrawer getPointDrawer() {
        return pointDrawer;
    }

    public void draw() {
        if (drawer == null || pointDrawer == null) {
            throw new IllegalStateException("Drawers is unset");
        }
        drawer.draw(this, color, this.pointDrawer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawableEllipse)) return false;
        if (!super.equals(o)) return false;
        DrawableEllipse that = (DrawableEllipse) o;
        return color.equals(that.color)
                && drawer.getClass().equals(that.drawer.getClass())
                && pointDrawer.getClass().equals(that.pointDrawer.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, drawer.getClass(), pointDrawer.getClass());
    }
}
