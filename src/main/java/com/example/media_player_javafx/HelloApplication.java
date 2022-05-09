package com.example.media_player_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        String icon_img = getClass().getResource("icon.png").toExternalForm();
        Image icon1 = new Image(icon_img);



        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Media Player");

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.getIcons().add(icon1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}