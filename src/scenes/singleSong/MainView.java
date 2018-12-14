package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.PlaylistManager;
import Exceptions.keinSongException;
import controlElements.ControlButtons;
import controlElements.VolumeAndTime;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Line;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EventListener;

public class MainView {

    boolean paused = true;
    public Scene buildScene(PlayerGUI gui, MP3Player player) {



        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));


        //PANES
        Pane song = new VBox();
        Pane bot = new HBox(8);
        bot.setPadding(new Insets(25, 0, 25, 0));
        ((VBox) song).setAlignment(Pos.CENTER);
        bot.setId("bot");


        //SLIDER
        Slider progress = new Slider();
        progress.setMin(0);
        progress.setMax(100);
        progress.setId("progress");

        //PROGRESSBAR
        ProgressBar pb1 = new ProgressBar(0.0);
        pb1.minWidth(0);
        pb1.setId("pb1");



        Line line = new Line();
        line.setStartX(0);
        line.getStyleClass().add("progressLine");
        line.endXProperty().bind(root.widthProperty());

        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, pb1);
        });
        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, pb1);
        });

        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll( line, pb1, progress);
        progressPane.setAlignment(Pos.CENTER_LEFT);
        progressPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        progressPane.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //PANE LEFT
        Text title = new Text(("Track" + " ").toUpperCase());
        title.getStyleClass().add("primarytext");


        Text interpret = new Text("Arctic Monkeys");
        interpret.getStyleClass().add("secondarytext");

        Pane songInfo = new HBox();
        songInfo.setMaxWidth(20);
        songInfo.setPrefWidth(20);
        songInfo.setMinWidth(20);
        songInfo.getChildren().addAll(title, interpret);
        ((HBox) songInfo).setAlignment(Pos.CENTER_LEFT);
        songInfo.setPadding(new Insets(0, 100, 0, 45));


        //VolumeAndTime

        HBox volumeAndTime = new HBox();

        ProgressBar pb2 = new ProgressBar(0.0);
        pb2.minWidth(0);
        pb2.setId("pb2");

        Slider volume = new Slider();
        volume.setId("volume");
        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(50);
        volume.setOrientation(Orientation.HORIZONTAL);
        volume.setPadding(new Insets(0, 0, 0, 10));
        volume.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
        });
        volume.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
            player.volume((newvar.floatValue() / 100));
        });

        Button mute = new Button();
        mute.getStyleClass().add("mute-button");
        mute.setStyle("-fx-shape: \"" + getPathFromSVG("mute") + "\";");
        mute.setAlignment(Pos.BASELINE_LEFT);
        //mute.setOnAction(muteButtonOnAction(volume));
        mute.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(volume.getValue()!= volume.getMin())
                volume.setValue(volume.getMin());
            else
                volume.setValue(volume.getMax()/2);
        });

        Line line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(100);
        line2.getStyleClass().add("progressLine");

        StackPane volumePane = new StackPane();
        volumePane.getChildren().addAll(volume,pb2,line2);
        volumePane.setAlignment(Pos.CENTER_LEFT);





        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        Text time = new Text();
        time.getStyleClass().add("secondarytext");

        Text songLength = new Text();
        songLength.setText(zeitanzeige.format(player.getSongLength()));
        long endTime = player.getSongLength();

        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 500 ),
                        event -> {


                            if ( player.getAktZeit() == 0 ) {

                                time.setText( zeitanzeige.format( 0 ) );
                            } else if (player.getAktZeit() == endTime){
                                try {
                                    player.next();
                                    title.setText(player.getTrack());
                                    interpret.setText(player.getSongArtist());
                                    songLength.setText(zeitanzeige.format(player.getSongLength()));
                                } catch (keinSongException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                time.setText( zeitanzeige.format( player.getAktZeit() ) );
                            }
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();



        volumeAndTime.getChildren().addAll(mute, volumePane, time);
        volumeAndTime.setAlignment(Pos.CENTER);
        volumeAndTime.setPadding(new Insets(0, 45, 0, 0));

        //ControlButtons

        HBox controlButtons = new HBox(20);
        Button play = new Button();
        play.getStyleClass().add("play-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
        play.setPadding(new Insets(0, 100, 0, 100));
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            try {
                if (player.isInitialized()) {
                    if (!player.isPlaying()) {
                        player.play();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");

                        title.setText(player.getTrack());
                        interpret.setText(player.getSongArtist());
                        time.setText("00/" + zeitanzeige.format(player.getSongLength()));
                    } else {
                        player.pause();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
                    }
                } else {
                    player.play();
                    play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                    title.setText(player.getTrack());
                    interpret.setText(player.getSongArtist());
                    time.setText(zeitanzeige.format(player.getSongLength()));
                }

            } catch (keinSongException e) {
                e.printStackTrace();
            }

        });

        Button previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");
        previous.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try{
                player.previous();
                title.setText(player.getTrack());
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            } catch( keinSongException e){

            }
        });

        Button next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");
        next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.next();
                title.setText(player.getTrack());
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            }  catch (keinSongException e) {
                e.printStackTrace();
            }
        });

        controlButtons.getChildren().addAll(previous, play, next);
        controlButtons.setAlignment(Pos.CENTER);
        GridPane.setHalignment(controlButtons, HPos.CENTER);

        //HINTERGRUND
        Rectangle progressBackground = new Rectangle();
        progressBackground.setId("progressBackground");
        progressBackground.setHeight(750);
        progressBackground.xProperty().bind(bot.widthProperty());


        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });
        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });

        //REGIONS
        Pane region = new HBox();
        region.setPrefWidth(0);
        region.setMinWidth(0);
        HBox.setHgrow(region, Priority.ALWAYS);

        Pane region2 = new HBox();
        region2.setPrefWidth(0);
        region2.setMinWidth(0);
        HBox.setHgrow(region2, Priority.ALWAYS);


        bot.getChildren().addAll(songInfo, region2, controlButtons, region, volumeAndTime);
        song.getChildren().addAll(progressPane, bot);
        ((VBox) song).setAlignment(Pos.BOTTOM_LEFT);
        StackPane test = new StackPane();
        test.getChildren().addAll(progressBackground, song);
        test.setAlignment(Pos.BOTTOM_LEFT);
        root.setBottom(test);


        Scene x = new Scene(root, 1024, 750);
        x.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        return x;
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

    private void changePause(){
        paused = !paused;
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
