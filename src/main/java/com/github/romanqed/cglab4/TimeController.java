package com.github.romanqed.cglab4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public final class TimeController implements Initializable {
    @FXML
    private VBox plane;

    private long calcTime(Ellipse ellipse, EllipseDrawer drawer) {
        return Util.calculateTime(() -> {
            for (int i = 0; i < 10_000; ++i) {
                drawer.draw(ellipse, Color.BLACK, (x, y, color) -> {});
            }
        });
    }

    private XYChart.Series<Number, Number> calcTime(String name, EllipseDrawer drawer) {
        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
        Point center = new Point(0, 0);
        for (int i = 1; i < 100; ++i) {
            Ellipse toDraw = new Ellipse(center, i, i);
            long time = calcTime(toDraw, drawer);
            data.add(new XYChart.Data<>(i, time));
        }
        XYChart.Series<Number, Number> ret = new XYChart.Series<>();
        ret.setName(name);
        ret.setData(data);
        return ret;
    }

    private LineChart<Number, Number> makeChart(String title, String prefix) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> ret = new LineChart<>(x, y);
        ret.setTitle(title);
        ret.getData().add(calcTime("Canonical", ReflectUtil.DRAWERS.get(prefix + "cl")));
        ret.getData().add(calcTime("Parametric", ReflectUtil.DRAWERS.get(prefix + "p")));
        ret.getData().add(calcTime("Bresenham", ReflectUtil.DRAWERS.get(prefix + "br")));
        ret.getData().add(calcTime("Middle point", ReflectUtil.DRAWERS.get(prefix + "mp")));
        return ret;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plane.getChildren().add(makeChart("Время работы (Окружность)", "c"));
        plane.getChildren().add(makeChart("Время работы (Эллипс)", ""));
    }
}
