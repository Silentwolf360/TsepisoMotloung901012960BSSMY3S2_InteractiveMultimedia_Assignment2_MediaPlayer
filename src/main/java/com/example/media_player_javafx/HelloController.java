package com.example.media_player_javafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloController {
    //setting the url of the media
    String video = getClass().getResource("/ED.mp4").toExternalForm();
    Media media = new Media(video);
    MediaPlayer mediaPlayer = new MediaPlayer(media);



    @FXML
    private VBox root;

    @FXML
    private MediaView media_view;


    @FXML
    private Label current_time;

    @FXML
    private Slider seek_slider;

    @FXML
    private Label total_duration;

    @FXML
    private Button stop_btn;

    @FXML
    private Button fast_backward;

    @FXML
    private Button play_or_pause;

    @FXML
    private HBox media_container;
    @FXML
    private Button fast_forward;

    @FXML
    private Slider volume_sliver = new Slider();

    @FXML
    private Button mute_btn;

    @FXML
    private Circle close_btn;

    @FXML
    private Circle minimize_btn;

    public void initialize(){

        //setting the media player
        media_view.setMediaPlayer(mediaPlayer);


        //setting the value of the slider to 100
        volume_sliver.setValue(mediaPlayer.getVolume() * 100);


        //changes the volume and slider position when dragged
        volume_sliver.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volume_sliver.getValue() / 100);
                mediaPlayer.setMute(false);
                mute_btn.setId("mute_btn");
            }
        });



        //setting listener for seek
        seek_slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (seek_slider.isPressed()) {
                long duration = newValue.intValue() * 1000;
                mediaPlayer.seek(new Duration(duration));
            }
        });



        //changes the slider position as the video plays and also changes the current play time
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!seek_slider.isValueChanging()) { //as the seek moves
                seek_slider.setValue(newTime.toSeconds());
                double current_sec = newTime.toSeconds(); //getting the current time in seconds

                //converting seconds to time
                int p1 = (int) (current_sec % 60);
                int p2 = (int) (current_sec / 60);
                int p3 = p2 % 60;
                p2 = p2 / 60;
                String current_time_string = ( p2 + ":" + p3 + ":" + p1);
                current_time.setText(current_time_string);

            }
        });


        //code to be executed when the media player is ready to play.
        //sets the seek slider to be equal to the duration and the total duration of the video is set
        mediaPlayer.setOnReady(() -> {

            seek_slider.setMax(mediaPlayer.getMedia().getDuration().toMillis() / 1000); //getting the maximum second of the media


            Double seconds = seek_slider.getMax();

            //converting seconds to time
            int p1 = (int) (seconds % 60);
            int p2 = (int) (seconds / 60);
            int p3 = p2 % 60;
            p2 = p2 / 60;
            String total_duration_string = ( p2 + ":" + p3 + ":" + p1);
            total_duration.setText(String.valueOf(total_duration_string));

        });

        mediaPlayer.setOnEndOfMedia(() -> {
            seek_slider.setValue(0);
            mediaPlayer.stop();
            mediaPlayer.setRate(1);
            play_or_pause.setId("play_btn");


        });

        //code to auto size the media view for every video
        media_view.autosize();
        media_view.fitWidthProperty().bind(media_container.widthProperty());


    }

    @FXML
    protected void fast_backwards_action() {
        Double rate = mediaPlayer.getRate();
        mediaPlayer.setRate(rate - (rate * 0.10));
    }

    @FXML
    void fast_forward_action(ActionEvent event) {
        Double rate = mediaPlayer.getRate();
        mediaPlayer.setRate(rate + (rate * 0.10));
    }

    @FXML
    void mute_action(ActionEvent event) {
        //setting the mute button to be mutable if and only if the mute is not on
        if(mediaPlayer.isMute()){
            mediaPlayer.setMute(false);
            mute_btn.setId("mute_btn");

        }else{
            mediaPlayer.setMute(true);
            mute_btn.setId("unmute_btn");

        }
    }

    @FXML
    void play_or_pause_action(ActionEvent event) {
        if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
            play_or_pause.setId("pause_btn");
            mediaPlayer.play();
        } else{
            //set the image to be play

            play_or_pause.setId("play_btn");
            mediaPlayer.pause();
        }

    }

    @FXML
    void seek_method(MouseEvent event) {

    }

    @FXML
    void show_error_message(ActionEvent event) {

    }

    @FXML
    void stop_action(ActionEvent event) {
        play_or_pause.setId("play_btn");
        mediaPlayer.stop();
        mediaPlayer.setRate(1);

    }

    @FXML
    void volume_slider_action(MouseEvent event) {

    }










    @FXML
    void close_action(MouseEvent event) {
        //setting the fade as the media player closes

        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(1500), new KeyValue(mute_btn.getScene().getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> Platform.exit()); //the system will exit when the timeline is done playing
        timeline.play();
    }

    @FXML
    void minimize_action(MouseEvent event) {
        ((Stage)((Button)play_or_pause).getScene().getWindow()).setIconified(true);
    }











}