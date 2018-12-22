package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Exceptions.keinSongException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.shape.Line;


import javax.swing.text.Style;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainView extends VBox{
    private boolean listenToScrollbar = false;
    private boolean paused = true;
    private boolean listenToProgress = true;
    private long countMillis = 0, firstMillis;

    private double volumePosition = 50;
    Slider progress, volume;
    Text time, songLength, interpret;
    DateFormat zeitanzeige;
    ProgressBar progressTimeSlider, progressBarVolume;
    Line progressTimeLine;
    Label titleInfo;
    StackPane progressPane, volumePane;
    HBox songInfo, volumeAndTime, songControl, controlButtons;

    Button play, previous, next, mute;
    Pane region, region2;
    double progressValue;

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
        time = new Text();
        time.getStyleClass().add("secondarytext");

        songLength = new Text();
        songLength.setText(zeitanzeige.format(player.getSongLength()));

        KeyFrame watchTimeLine = new KeyFrame(
                Duration.millis(50),
                event -> {


                    if (player.getAktZeit() == 0) {

                        time.setText(zeitanzeige.format(0) + "/" + zeitanzeige.format(player.getSongLength()));

                    }else
                     {
                        time.setText(zeitanzeige.format(player.getAktZeit()) + "/" + zeitanzeige.format(player.getSongLength()));

                        if(! progress.isValueChanging())
                            progress.setValue(((double) player.getAktZeit() / (double) player.getSongLength()) * 100);

                    }

                }
        );



        final Timeline timeline = new Timeline(watchTimeLine);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        progress.addEventHandler(MouseEvent.MOUSE_PRESSED, event->{

            progressValue = progress.getValue();

        });

        //PROGRESSBAR
        progressTimeSlider = new ProgressBar(0.0);
        progressTimeSlider.minWidth(0);
        progressTimeSlider.setId("pb1");

        progressTimeLine = new Line();
        progressTimeLine.setStartX(0);
        progressTimeLine.getStyleClass().add("progressLine");
        progressTimeLine.endXProperty().bind(this.widthProperty());

        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, progressTimeSlider);
        });

        //PANE LEFT
        interpret = new Text("Arctic Monkeys");
        interpret.getStyleClass().addAll("secondarytext");


        titleInfo = new Label();
        titleInfo.setStyle("-fx-font-weight:bold;");
        titleInfo.setStyle("-fx-text-fill:#74CCDB;");


        progress.valueChangingProperty().addListener((observable, wasChanging, isChanging)->{

            if(!isChanging) {
                player.skip((int) ((progress.getValue() / 100 * player.getSongLength()) - progressValue / 100 * player.getSongLength()));
                if (!player.isPlayerActive()) {
                    try {
                        player.pause();
                    } catch (keinSongException e) {
                        e.printStackTrace();
                    }
                }
            }

        });



        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            if( ! progress.isValueChanging()) {
                int currentTime = player.getAktZeit();
               if(Math.abs(currentTime - (newvar.doubleValue() / 100 * player.getSongLength())) > 50){
                   player.skip((int) ((newvar.doubleValue() / 100 * player.getSongLength()) - oldvar.doubleValue() / 100 * player.getSongLength()));
                   if (!player.isPlayerActive()) {
                       try {
                           player.pause();
                       } catch (keinSongException e) {
                           e.printStackTrace();
                       }
                   }
               }
            }

            if (newvar.intValue() == 0) {

                titleInfo.setText(player.getTrack());
                interpret.setText(player.getSongArtist() + " ");
            }
            calculatePB(progress, progressTimeSlider);
        });




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
        mute.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (volume.getValue() != volume.getMin()){
                volumePosition = volume.getValue();
                volume.setValue(volume.getMin());
            }
            else
                volume.setValue(volumePosition);
        });


        volume.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePBForVolume(volume, progressBarVolume);

        });
        volume.valueProperty().addListener((observable, oldvar, newvar) -> {
            if(newvar.doubleValue() == 0){
                mute.setStyle("-fx-shape:\""+ getPathFromSVG("mute2") + "\";");
            }
                else{
                mute.setStyle("-fx-shape:\""+ getPathFromSVG("mute") + "\";");
            }
            calculatePBForVolume(volume, progressBarVolume);
            player.volume((newvar.floatValue() / 100));
        });


        volumePane = new StackPane();
        volumePane.getChildren().addAll( progressBarVolume, volume); //, progressBarVolume, line2
        volumePane.setAlignment(Pos.CENTER_LEFT);
        volumePane.setPadding(new Insets(0, 10, 0, 10));


        volumeAndTime.getChildren().addAll(mute, volumePane, time);
        volumeAndTime.setAlignment(Pos.CENTER_RIGHT);
        volumeAndTime.setPadding(new Insets(0, 45, 0, 0));
        volumeAndTime.setMaxWidth(songControl.getWidth() / 4);
        volumeAndTime.setMinWidth(songControl.getWidth() / 4);
        volumeAndTime.setPrefWidth(songControl.getWidth() / 4);


        songControl.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                volumeAndTime.setMaxWidth(songControl.getWidth() / 3);
                volumeAndTime.setMinWidth(songControl.getWidth() / 3);
                volumeAndTime.setPrefWidth(songControl.getWidth() / 3);
                songInfo.setMaxWidth(songControl.getWidth() / 3);
                songInfo.setMinWidth(songControl.getWidth() / 3);
                songInfo.setPrefWidth(songControl.getWidth() / 3);
            }
        });


        //ControlButtons

        controlButtons = new HBox(20);
        play = new Button();
        play.getStyleClass().add("play-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
        play.setPadding(new Insets(0, 100, 0, 100));

        if(player.isPlayerActive())
            play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");

        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            try {
                if (player.isInitialized()) {
                    if (!player.isPlayerActive()) {
                        player.play();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                        interpret.setText(player.getSongArtist());
                    } else {
                        player.pause();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
                    }
                } else {
                    player.play();
                    play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                    interpret.setText(player.getSongArtist());
                }

            } catch (keinSongException e) {
                e.printStackTrace();
            }

        });

        previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");
        previous.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {


                player.previous();
                play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            } catch (keinSongException e) {

            }
        });

        next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");
        next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {


                player.next();
                play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });

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

    private void changeListenToProgressTrue() {
        listenToProgress = true;
    }

    private void changeListenToProgressFalse() {
        listenToProgress = false;
    }

    private String getFirstSongFromPlaylist(String x) {
        String zeile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(x));
            if ((zeile = reader.readLine()) != null){
                return zeile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nichts gefunden";
    }






    private static String getPathFromSVG(String filename){
        String d = "abc";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder =  factory.newDocumentBuilder();
            Document doc = builder.parse("resources/icons/"+ filename+".svg");
            NodeList elemente = doc.getElementsByTagName("path");
            Element element = (Element) elemente.item(0);

            return element.getAttribute("d");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    private void calculatePB(Slider progress, ProgressBar bar1) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        bar1.setProgress(1);


        if(actValue == half){
            bar1.setMinWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(width-minwidth);
            bar1.setMaxWidth(width-minwidth);

        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(minwidth);
            bar1.setMaxWidth(minwidth);
        }



    }

    private void calculatePBForVolume(Slider progress, ProgressBar bar1) {
        double actValue = progress.getValue() - 1;
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        bar1.setProgress(1);


        if(actValue == half){
            bar1.setMinWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(width-minwidth);
            bar1.setMaxWidth(width-minwidth);

        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(minwidth);
            bar1.setMaxWidth(minwidth);
        }



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
