package scenes.singleSong;

import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static scenes.singleSong.MainViewController.getPathFromSVG;

public class MainViewMobile extends VBox {

    Slider progress, volume;
    Button play, previous, next, mute;
    DateFormat zeitanzeige;
    Text time, songLength;
    ProgressBar progressTimeSlider, progressBarVolume;
    Line progressTimeLine;
    Label interpret, titleInfo;

    public MainViewMobile (MP3Player player){
        progress = new Slider();
        progress.setMin(0);
        progress.setMax(100);
        progress.setId("progress");

        HBox controlButtons = new HBox(25);

        play = new Button();
        play.getStyleClass().add("play-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");

        previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");

        next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");

        controlButtons.getChildren().addAll(previous, play, next);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setPadding(new Insets(15, 0, 15, 0));
        controlButtons.setPrefHeight(100);
        controlButtons.setMinHeight(100);
        controlButtons.setMaxHeight(100);

        HBox timeBox = new HBox();
        HBox songLengthBox = new HBox();

        zeitanzeige = new SimpleDateFormat("mm:ss");

        time = new Text("00:00");
        time.getStyleClass().add("secondarytext");
        time.setTextAlignment(TextAlignment.LEFT);

        songLength = new Text();
        songLength.setText(zeitanzeige.format(player.getSongLength()));
        songLength.getStyleClass().add("secondarytext");

        timeBox.getChildren().add(time);
        timeBox.setPadding(new Insets(0,0,0,15));
        timeBox.setAlignment(Pos.CENTER_LEFT);

        songLengthBox.getChildren().add(songLength);
        songLengthBox.setPadding(new Insets(0, 15, 0, 0));
        songLengthBox.setAlignment(Pos.CENTER_RIGHT);

        HBox region = new HBox();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox allTimeBox = new HBox();
        allTimeBox.getChildren().addAll(timeBox, region,  songLengthBox);


        progressTimeSlider = new ProgressBar(0.0);
        progressTimeSlider.minWidth(0);
        progressTimeSlider.setId("pb1");

        progressTimeLine = new Line();
        progressTimeLine.setStartX(0);
        progressTimeLine.getStyleClass().add("progressLine");
        progressTimeLine.endXProperty().bind(this.widthProperty());

        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll(progressTimeLine, progressTimeSlider, progress);
        progressPane.setAlignment(Pos.CENTER_LEFT);
        progressPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        progressPane.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        progressPane.setPadding(new Insets(0, 0, 5, 0));

        interpret = new Label(player.getSongArtist());
        interpret.setStyle("-fx-font-weight:lighter;");
        interpret.setStyle("-fx-text-fill:#74CCDB;");
        interpret.getStyleClass().addAll("secondarytext");

        titleInfo = new Label(player.getTrack());
        titleInfo.setStyle("-fx-font-weight:bold;");
        titleInfo.setStyle("-fx-text-fill:#74CCDB;");
        titleInfo.setPadding(new Insets(0, 60, 0, 60));

        volume = new Slider();
        volume.setId("volume");
        volume.getStyleClass().add("pb2");
        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(50);
        volume.setOrientation(Orientation.HORIZONTAL);

        progressBarVolume = new ProgressBar(0.0);
        progressBarVolume.setMinWidth(volume.getMinWidth() + 5);
        progressBarVolume.setMaxWidth(volume.getMaxWidth() - 10);
        progressBarVolume.setId("pb2");

        /*
        Line line2 = new Line();
        line2.setStartX(0);
        HBox.setHgrow(line2, Priority.ALWAYS);
        line2.getStyleClass().add("progressLine");
        */

        mute = new Button();
        mute.getStyleClass().add("mute-button");
        mute.setStyle("-fx-shape: \"" + getPathFromSVG("mute") + "\";");


        StackPane volumePane = new StackPane();
        volumePane.getChildren().addAll( progressBarVolume, volume);
        volumePane.setAlignment(Pos.CENTER_LEFT);
        volumePane.setPadding(new Insets(0, 10, 0, 10));

        HBox volumeElement = new HBox(10);
        volumeElement.getChildren().addAll(mute, volumePane);
        volumeElement.setAlignment(Pos.CENTER);
        volumeElement.setPadding(new Insets(0, 0, 15, 0));

        this.getChildren().addAll(progressPane, allTimeBox, titleInfo, interpret, controlButtons, volumeElement);
        this.setAlignment(Pos.CENTER);

    }

}
