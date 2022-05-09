module com.example.media_player_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.media_player_javafx to javafx.fxml;
    exports com.example.media_player_javafx;
}