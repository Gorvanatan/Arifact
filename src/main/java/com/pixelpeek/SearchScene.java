package com.pixelpeek;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SearchScene {

    public static void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #0f0f0f;");

        VBox centerBox = new VBox(16);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(500);

        Label title = new Label("PixelPeek");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 46));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Enter a word");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.web("#777777"));

        TextField searchField = new TextField();
        searchField.setPromptText("");
        searchField.setStyle(
            "-fx-background-color: #1a1a1a;" +
            "-fx-border-color: #333333;" +
            "-fx-border-radius: 30;" +
            "-fx-background-radius: 30;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #555555;" +
            "-fx-font-size: 18px;" +
            "-fx-padding: 14 24 14 24;"
        );
        searchField.setMaxWidth(460);

        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.web("#ff6b6b"));
        errorLabel.setFont(Font.font("Arial", 13));

        searchField.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) return;

            errorLabel.setText("");
            searchField.setDisable(true);
            searchField.setPromptText("searching...");
            SoundManager.playSearch();

            // Run API call off the UI thread
            Thread thread = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                try {
                    PhotoData data = UnsplashService.search(query);
                    long elapsed = System.currentTimeMillis() - startTime;
                    long minDelay = 2500; // minimum 2.5 seconds for the "searching" illusion
                    if (elapsed < minDelay) {
                        Thread.sleep(minDelay - elapsed);
                    }
                    javafx.application.Platform.runLater(() -> {
                        SoundManager.playSuccess();
                        ResultScene.show(data);
                    });
                } catch (Exception ex) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    long minDelay = 2500;
                    if (elapsed < minDelay) {
                        try { Thread.sleep(minDelay - elapsed); } catch (InterruptedException ignored) {}
                    }
                    javafx.application.Platform.runLater(() -> {
                        SoundManager.playError();
                        errorLabel.setText("⚠  " + ex.getMessage());
                        searchField.setDisable(false);
                        searchField.setText("");
                        searchField.setPromptText("");
                        searchField.requestFocus();
                    });
                }
            });
            thread.setDaemon(true);
            thread.start();
        });

        centerBox.getChildren().addAll(title, subtitle, searchField, errorLabel);
        root.getChildren().add(centerBox);

        Scene scene = new Scene(root, 700, 780);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();

        // Auto-focus the search field
        javafx.application.Platform.runLater(searchField::requestFocus);
    }
}