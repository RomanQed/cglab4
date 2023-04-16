package com.github.romanqed.cglab4;

import java.util.Objects;

public class Ellipse {
    final Point center;
    final double a;
    final double b;

    public Ellipse(Point center, double a, double b) {
        this.center = center;
        this.a = a;
        this.b = b;
    }

    public Point getCenter() {
        return center;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ellipse)) return false;
        Ellipse ellipse = (Ellipse) o;
        return Double.compare(ellipse.a, a) == 0
                && Double.compare(ellipse.b, b) == 0
                && center.equals(ellipse.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, a, b);
    }
}
