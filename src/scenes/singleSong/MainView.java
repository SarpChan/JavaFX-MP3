package scenes.singleSong;

import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static scenes.singleSong.MainViewController.getPathFromSVG;

public class MainView extends VBox{

    Slider progress, volume;
    Text time, songLength;
    DateFormat zeitanzeige;
    ProgressBar progressTimeSlider, progressBarVolume;
    Line progressTimeLine;
    Label titleInfo, interpret;
    StackPane progressPane, volumePane;
    HBox songInfo, volumeAndTime, songControl, controlButtons;

    Button play, previous, next, mute;
    Pane region, region2;


    public MainView(MP3Player player) {



        songControl = new HBox();


        songControl.setPadding(new Insets(25, 0, 25, 0));
        songControl.setId("songControl");


        //SLIDER
        progress = new Slider();
        progress.setMin(0);
        progress.setMax(100);
        progress.setId("progress");

        zeitanzeige = new SimpleDateFormat("mm:ss");
        time = new Text("00:00");
        time.getStyleClass().add("secondarytext");

        songLength = new Text();
        songLength.setText(zeitanzeige.format(player.getSongLength()));
        songLength.getStyleClass().add("secondarytext");

        Text slash = new Text(" / ");
        slash.getStyleClass().add("secondarytext");

        HBox timeLabel = new HBox();
        timeLabel.getChildren().addAll(time, slash, songLength);
        timeLabel.setAlignment(Pos.CENTER_RIGHT);

        //PROGRESSBAR
        progressTimeSlider = new ProgressBar(0.0);
        progressTimeSlider.minWidth(0);
        progressTimeSlider.setId("pb1");

        progressTimeLine = new Line();
        progressTimeLine.setStartX(0);
        progressTimeLine.getStyleClass().add("progressLine");
        progressTimeLine.endXProperty().bind(this.widthProperty());

        //PANE LEFT
        interpret = new Label();
        interpret.setStyle("-fx-font-weight:lighter;");
        interpret.setStyle("-fx-text-fill:#74CCDB;");
        interpret.getStyleClass().addAll("secondarytext");


        titleInfo = new Label();
        titleInfo.setStyle("-fx-font-weight:bold;");
        titleInfo.setStyle("-fx-text-fill:#74CCDB;");


        progressPane = new StackPane();
        progressPane.getChildren().addAll(progressTimeLine, progressTimeSlider, progress);
        progressPane.setAlignment(Pos.CENTER_LEFT);
        progressPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        progressPane.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);

        songInfo = new HBox(10);

        songInfo.setMaxWidth(songControl.getWidth() / 4);
        songInfo.setMinWidth(songControl.getWidth() / 4);
        songInfo.setPrefWidth(songControl.getWidth() / 4);

        songInfo.getChildren().addAll(interpret, titleInfo);
        songInfo.setAlignment(Pos.CENTER_LEFT);
        songInfo.setPadding(new Insets(0, 0, 0, 45));
        songInfo.getStyleClass().add("song-info");
        songInfo.setStyle("-overflow:hidden;");

        //VolumeAndTime

        volumeAndTime = new HBox();

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


        volumePane = new StackPane();
        volumePane.getChildren().addAll( progressBarVolume, volume); //, progressBarVolume, line2
        volumePane.setAlignment(Pos.CENTER_LEFT);
        volumePane.setPadding(new Insets(0, 10, 0, 10));


        volumeAndTime.getChildren().addAll(mute, volumePane, timeLabel);
        volumeAndTime.setAlignment(Pos.CENTER_RIGHT);
        volumeAndTime.setPadding(new Insets(0, 45, 0, 0));
        volumeAndTime.setMaxWidth(songControl.getWidth() / 4);
        volumeAndTime.setMinWidth(songControl.getWidth() / 4);
        volumeAndTime.setPrefWidth(songControl.getWidth() / 4);


        songControl.widthProperty().addListener((observable, oldValue, newValue) -> {
            volumeAndTime.setMaxWidth(songControl.getWidth() / 3);
            volumeAndTime.setMinWidth(songControl.getWidth() / 3);
            volumeAndTime.setPrefWidth(songControl.getWidth() / 3);
            songInfo.setMaxWidth(songControl.getWidth() / 3);
            songInfo.setMinWidth(songControl.getWidth() / 3);
            songInfo.setPrefWidth(songControl.getWidth() / 3);
        });


        //Buttons

        controlButtons = new HBox(20);
        play = new Button();
        play.getStyleClass().add("play-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
        play.setPadding(new Insets(0, 100, 0, 100));



        previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");


        next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");


        controlButtons.getChildren().addAll(previous, play, next);
        controlButtons.setAlignment(Pos.CENTER);

        //HINTERGRUND
       /* Rectangle progressBackground = new Rectangle();
        progressBackground.setId("progressBackground");
        progressBackground.setHeight(750);
        root.heightProperty().addListener((observable, oldValue, newValue) -> progressBackground.setHeight(newValue.doubleValue()));
        progressBackground.xProperty().bind(songControl.widthProperty());


        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });
        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });

        */

        //REGIONS
        region = new HBox();
        region.setPrefWidth(0);
        region.setMinWidth(0);
        HBox.setHgrow(region, Priority.ALWAYS);

        region2 = new HBox();
        region2.setPrefWidth(0);
        region2.setMinWidth(0);
        HBox.setHgrow(region2, Priority.ALWAYS);


        songControl.getChildren().addAll(songInfo, region2, controlButtons, region, volumeAndTime);
        songControl.setMinHeight(105);

        this.getChildren().addAll(progressPane, songControl);
        this.setAlignment(Pos.BOTTOM_LEFT);

    }

    private void calculateBackgroundProgress(Slider progress, Rectangle bg) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        if(actValue == half){
            bg.setWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(width-minwidth);


        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(minwidth);
        }

    }

}
