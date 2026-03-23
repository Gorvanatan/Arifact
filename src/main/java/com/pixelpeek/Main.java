package com.pixelpeek;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("PixelPeek");
        stage.setWidth(700);
        stage.setHeight(780);
        stage.setResizable(false);

        SoundManager.init();
        SoundManager.playStartup();

        SearchScene.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}