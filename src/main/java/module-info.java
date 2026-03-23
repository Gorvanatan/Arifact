module com.pixelpeek {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.net.http;
    requires com.google.gson;

    opens com.pixelpeek to javafx.fxml;
    exports com.pixelpeek;
}
