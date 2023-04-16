package com.github.romanqed.cglab4;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;


public class MainController implements Initializable {
    private static final double GRID_SIZE = 100;

    private final ActionPool pool = new ActionPool();
    public ToggleGroup method;

    @FXML
    protected Canvas canvas;

    private GraphicsContext context;

    private Map<String, EllipseDrawer> drawers;

    @FXML
    protected TextField cx;

    @FXML
    protected TextField cy;

    @FXML
    protected TextField r1;

    @FXML
    protected TextField r2;

    @FXML
    protected TextField step;

    @FXML
    protected CheckBox ellipse;

    @FXML
    protected ColorPicker backgroundPicker;

    @FXML
    protected ColorPicker linePicker;

    @FXML
    protected TextField amount;

    private Set<DrawableEllipse> ellipses;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.backgroundPicker.setValue(Color.WHITE);
        this.linePicker.setValue(Color.BLACK);
        this.context = canvas.getGraphicsContext2D();
        this.drawers = new HashMap<>();
        this.drawers.putAll(ReflectUtil.DRAWERS);
        this.drawers.put("jfx", (ellipse, color, drawer) -> {
            this.context.setStroke(color);
            Point center = ellipse.center;
            this.context.strokeOval(center.x - ellipse.a,
                    center.y - ellipse.b,
                    ellipse.a * 2,
                    ellipse.b * 2);
        });
        this.drawers.put("cjfx", (ellipse, color, drawer) -> {
            this.context.setStroke(color);
            double radius = ellipse.a;
            Point center = ellipse.center;
            this.context.strokeOval(center.x - radius,
                    center.y - radius,
                    radius * 2,
                    radius * 2
                    );
        });
        this.ellipses = new HashSet<>();
        this.refresh();
    }

    private void commit(DrawableEllipse line) {
        Set<DrawableEllipse> toAdd = new HashSet<>();
        toAdd.add(line);
        commit(toAdd);
    }

    private void commit(Set<DrawableEllipse> toAdd) {
        pool.add(new ActionImpl(toAdd));
    }

    private boolean isEllipse() {
        return ellipse.isSelected();
    }

    private void drawGrid(Color color) {
        context.setLineWidth(0.5);
        context.setStroke(color);
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        for (double i = GRID_SIZE; i < width; i += GRID_SIZE) {
            context.strokeLine(0, i, width, i);
        }
        for (double i = GRID_SIZE; i < height; i += GRID_SIZE) {
            context.strokeLine(i, 0, i, height);
        }
    }

    private EllipseDrawer getEllipseDrawer() {
        Toggle toggle = method.getSelectedToggle();
        String key = (String) toggle.getUserData();
        if (!isEllipse()) {
            key = "c" + key;
        }
        return drawers.get(key);
    }

    static Color reverse(Color color) {
        return Color.color(1 - color.getRed(), 1 - color.getGreen(), 1 - color.getBlue());
    }

    private void refresh() {
        Color background = backgroundPicker.getValue();
        context.setFill(background);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawGrid(reverse(background));
        context.setLineWidth(1);
        for (DrawableEllipse ellipse : ellipses) {
            ellipse.draw();
        }
    }

    private <T> T parse(TextField textField, Function<String, T> parser) {
        try {
            return parser.apply(textField.getText());
        } catch (Throwable e) {
            return null;
        }
    }

    private Point getPoint(TextField xField, TextField yField) {
        Double x = parse(xField, Double::parseDouble);
        Double y = parse(yField, Double::parseDouble);
        if (x == null || y == null) {
            return null;
        }
        return new Point(x, y);
    }

    private DrawableEllipse getEllipse() {
        Double rA = parse(r1, Double::parseDouble);
        if (rA == null) {
            Util.showError("Ошибка", "Радиус 1 не является числом");
            return null;
        }
        Double rB = isEllipse() ? parse(r2, Double::parseDouble) : rA;
        if (rB == null) {
            Util.showError("Ошибка", "Радиус 2 не является числом");
            return null;
        }
        Point center = getPoint(cx, cy);
        if (center == null) {
            Util.showError("Ошибка", "Координаты центра не являются числом");
            return null;
        }
        DrawableEllipse ret = new DrawableEllipse(center, rA, rB);
        ret.setColor(linePicker.getValue());
        ret.setDrawer(getEllipseDrawer());
        ret.setPointDrawer((x, y, color) -> {
            context.setStroke(color);
            context.strokeLine(x, y, x, y);
        });
        return ret;
    }

    @FXML
    protected void onDraw() {
        DrawableEllipse toAdd = getEllipse();
        if (toAdd == null) {
            return;
        }
        commit(toAdd);
    }

    @FXML
    protected void onExitAction() {
        System.exit(0);
    }

    @FXML
    protected void onAboutAction() throws IOException {
        Util.showInfo("О программе", Util.readResourceFile("about.txt"));
    }

    @FXML
    protected void onTimeCompare() throws IOException {
        Stage stage = Util.loadStage("time.fxml", 0, 0);
        stage.setResizable(false);
        stage.setTitle("Время работы");
        stage.show();
    }

    @FXML
    protected void onBeam() {
        DrawableEllipse start = getEllipse();
        if (start == null) {
            return;
        }
        Point center = start.center;
        Double step = parse(this.step, Double::parseDouble);
        if (step == null) {
            Util.showError("Ошибка", "Шаг не является числом");
            return;
        }
        Long count = parse(this.amount, Long::parseLong);
        if (count == null) {
            Util.showError("Ошибка", "Количество фигур не является числом");
            return;
        }
        Set<DrawableEllipse> toAdd = new HashSet<>();
        toAdd.add(start);
        double a = start.a;
        double b = start.b;
        for (long i = 1; i < count; ++i) {
            DrawableEllipse ellipse = new DrawableEllipse(center, a, b);
            ellipse.setColor(start.getColor());
            ellipse.setDrawer(start.getDrawer());
            ellipse.setPointDrawer(start.getPointDrawer());
            toAdd.add(ellipse);
            a += step;
            b += step;
        }
        commit(toAdd);
    }

    @FXML
    protected void onAuthorAction() {
        Util.showInfo("Автор", "Бакалдин Роман ИУ7-45Б");
    }

    @FXML
    protected void onCancelAction() {
        this.pool.undo();
    }

    @FXML
    protected void onRedoAction() {
        this.pool.redo();
    }

    @FXML
    protected void onResetAction() {
        this.pool.clear();
        this.ellipses.clear();
        this.refresh();
    }

    @FXML
    protected void onCanvasMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        cx.setText(String.format("%.2f", x).replace(',', '.'));
        cy.setText(String.format("%.2f", y).replace(',', '.'));
    }

    private final class ActionImpl implements Action {
        private final Set<DrawableEllipse> backup;

        private ActionImpl(Set<DrawableEllipse> ellipses) {
            this.backup = ellipses;
        }

        @Override
        public void perform() {
            ellipses.addAll(backup);
            refresh();
        }

        @Override
        public void undo() {
            ellipses.removeAll(backup);
            refresh();
        }
    }
}
