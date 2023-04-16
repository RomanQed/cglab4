package com.github.romanqed.cglab4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.TreeSet;

public class MainApplication extends Application {
    public static void main(String[] args) {
        TreeSet<Integer> l;
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Лабораторная работа №4");
        stage.setScene(scene);
        stage.show();
    }
}
