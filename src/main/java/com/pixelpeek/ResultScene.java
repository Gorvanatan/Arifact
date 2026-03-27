package com.pixelpeek;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.awt.Desktop;
import java.net.URI;

public class ResultScene {

    public static void show(PhotoData data) {

        // ── Root layout ──────────────────────────────────────────────────────
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #0f0f0f;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: #0f0f0f; -fx-background-color: #0f0f0f;");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(28);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 40, 50, 40));
        content.setStyle("-fx-background-color: #0f0f0f;");

        // ── Image ────────────────────────────────────────────────────────────
        ImageView imageView = new ImageView();
        imageView.setFitWidth(620);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setOpacity(0);

        // Rounded clip on the image
        Rectangle clip = new Rectangle(620, 400);
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        imageView.setClip(clip);

        Image image = new Image(data.imageUrl, true);
        imageView.setImage(image);

        // Adjust clip height once image loads
        image.progressProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() == 1.0) {
                double ratio = image.getHeight() / image.getWidth();
                clip.setHeight(620 * ratio);

                FadeTransition fade = new FadeTransition(Duration.millis(900), imageView);
                fade.setFromValue(0);
                fade.setToValue(1);
                fade.play();
            }
        });

        // ── Info panel (hidden initially) ────────────────────────────────────
        VBox infoBox = new VBox(0);
        infoBox.setOpacity(0);
        infoBox.setMaxWidth(620);
        infoBox.setStyle(
            "-fx-background-color: #161616;" +
            "-fx-border-color: #2b2b2b;" +
            "-fx-border-radius: 14;" +
            "-fx-background-radius: 14;"
        );

        // Panel header
        HBox header = new HBox();
        header.setPadding(new Insets(18, 22, 18, 22));
        header.setStyle(
            "-fx-background-color: #1e1e1e;" +
            "-fx-background-radius: 14 14 0 0;" +
            "-fx-border-color: transparent transparent #2b2b2b transparent;"
        );
        Label headerLabel = new Label("📋  Photo Details");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        headerLabel.setTextFill(Color.WHITE);
        header.getChildren().add(headerLabel);

        VBox rows = new VBox(0);
        rows.setPadding(new Insets(6, 0, 6, 0));

        rows.getChildren().addAll(
            infoRow("🔍  Search Query",   capitalise(data.query)),
            divider(),
            infoRow("📐  Dimensions",      data.width + " × " + data.height + " px"),
            divider(),
            colorRow("🎨  Dominant Color", data.color),
            divider(),
            infoRow("🖼   File Format",    "JPEG"),
            divider(),
            infoRow("📝  Description",     capitalise(data.description)),
            divider(),
            infoRow("📸  Photographer",    data.photographerName),
            divider(),
            linkRow("🔗  Source",          data.photographerUrl)
        );

        infoBox.getChildren().addAll(header, rows);

        // ── Search Again button (hidden initially) ────────────────────────────
        Button searchAgainBtn = new Button("Search Again");
        searchAgainBtn.setOpacity(0);
        searchAgainBtn.setTranslateY(10);
        String btnBase =
            "-fx-background-color: #ffffff;" +
            "-fx-text-fill: #0f0f0f;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 13 40 13 40;" +
            "-fx-background-radius: 30;" +
            "-fx-cursor: hand;";
        String btnHover =
            "-fx-background-color: #dddddd;" +
            "-fx-text-fill: #0f0f0f;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 13 40 13 40;" +
            "-fx-background-radius: 30;" +
            "-fx-cursor: hand;";

        searchAgainBtn.setStyle(btnBase);
        searchAgainBtn.setOnMouseEntered(e -> searchAgainBtn.setStyle(btnHover));
        searchAgainBtn.setOnMouseExited(e  -> searchAgainBtn.setStyle(btnBase));
        searchAgainBtn.setOnAction(e -> SearchScene.show());

        content.getChildren().addAll(imageView, infoBox, searchAgainBtn);
        scrollPane.setContent(content);
        root.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 700, 780);
        Main.primaryStage.setScene(scene);

        // ── Animation timeline ────────────────────────────────────────────────
        // After 2 s → info panel fades in
        PauseTransition waitForInfo = new PauseTransition(Duration.seconds(2));
        waitForInfo.setOnFinished(e -> {
            FadeTransition infoFade = new FadeTransition(Duration.millis(700), infoBox);
            infoFade.setFromValue(0);
            infoFade.setToValue(1);
            infoFade.play();

            // After 5 s total (3 s more) → button appears
            PauseTransition waitForBtn = new PauseTransition(Duration.seconds(3));
            waitForBtn.setOnFinished(ev -> {
                FadeTransition btnFade = new FadeTransition(Duration.millis(500), searchAgainBtn);
                btnFade.setFromValue(0);
                btnFade.setToValue(1);

                TranslateTransition btnSlide = new TranslateTransition(Duration.millis(500), searchAgainBtn);
                btnSlide.setFromY(10);
                btnSlide.setToY(0);

                btnFade.play();
                btnSlide.play();
            });
            waitForBtn.play();
        });
        waitForInfo.play();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static HBox infoRow(String label, String value) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14, 22, 14, 22));

        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        labelNode.setTextFill(Color.web("#888888"));
        labelNode.setMinWidth(175);

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("Arial", 13));
        valueNode.setTextFill(Color.WHITE);
        valueNode.setWrapText(true);
        valueNode.setMaxWidth(380);

        row.getChildren().addAll(labelNode, valueNode);
        return row;
    }

    private static HBox colorRow(String label, String hexColor) {
        HBox row = infoRow(label, hexColor.toUpperCase());

        // Add a small color swatch at the end
        Rectangle swatch = new Rectangle(18, 18);
        swatch.setArcWidth(5);
        swatch.setArcHeight(5);
        try {
            swatch.setFill(Color.web(hexColor));
        } catch (Exception e) {
            swatch.setFill(Color.GRAY);
        }
        swatch.setStroke(Color.web("#444444"));
        swatch.setStrokeWidth(1);
        row.getChildren().add(swatch);
        return row;
    }

    private static Separator divider() {
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #232323;");
        sep.setPadding(new Insets(0, 22, 0, 22));
        return sep;
    }

    private static HBox linkRow(String label, String url) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14, 22, 14, 22));

        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        labelNode.setTextFill(Color.web("#888888"));
        labelNode.setMinWidth(175);

        Label linkNode = new Label(url);
        linkNode.setFont(Font.font("Arial", 13));
        linkNode.setTextFill(Color.web("#4ea8f5"));
        linkNode.setStyle("-fx-cursor: hand;");
        linkNode.setWrapText(true);
        linkNode.setMaxWidth(380);
        linkNode.setOnMouseEntered(e -> linkNode.setTextFill(Color.web("#80c4ff")));
        linkNode.setOnMouseExited(e  -> linkNode.setTextFill(Color.web("#4ea8f5")));
        linkNode.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        row.getChildren().addAll(labelNode, linkNode);
        return row;
    }

    private static String capitalise(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}