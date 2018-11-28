package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.PlaylistManager;
import Exceptions.keinSongException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.control.ProgressBar;;
import javafx.scene.shape.Line;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        // song.setPrefHeight(300);
        //song.setMinHeight(300);
        //((VBox) song).setSpacing(20);
        //((HBox) bot).setAlignment(Pos.CENTER);
        //bot.setPrefHeight(20);


        //SLIDER
        Slider progress = new Slider();
        progress.setMin(0);
        progress.setMax(100);
        progress.setId("progress");

        //PROGRESSBAR
        Pane progressBarPane = new Pane();
        ProgressBar pb1 = new ProgressBar(0.0);
        pb1.minWidth(0);
        pb1.setId("pb1");

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(1024);
        line.getStyleClass().add("progressLine");

        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, pb1);
        });
        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, pb1);
        });

        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll( line, pb1, progress);
        progressPane.setAlignment(Pos.CENTER_LEFT);


        //PANE LEFT
        Text title = new Text(("Track" + " ").toUpperCase());
        title.getStyleClass().add("primarytext");

        Text interpret = new Text("Arctic Monkeys");
        //title.setFill(Color.rgb(116, 204, 219, 1));
        // interpret.setFill(Color.rgb(85, 129, 137, 1));
        interpret.getStyleClass().add("secondarytext");


        Pane songInfo = new HBox();
        songInfo.getChildren().addAll(title, interpret);
        ((HBox) songInfo).setAlignment(Pos.CENTER_LEFT);
        songInfo.setPadding(new Insets(0, 0, 0, 45));

        //PANE MIDDLE
        Button play = new Button("play");
        Button previous = new Button("prev");
        Button next = new Button("next");

        Pane controlButtons = new HBox();
        controlButtons.getChildren().addAll(previous, play, next);
        ((HBox) controlButtons).setAlignment(Pos.CENTER);

        //PANE RIGHT
        Button mute = new Button("mute");
        Slider volume = new Slider();
        volume.setId("volume");
        volume.setMin(0);
        volume.setOrientation(Orientation.HORIZONTAL);

        ProgressBar pb2 = new ProgressBar(0.0);
        pb2.minWidth(0);
        pb2.setId("pb2");

        volume.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
        });
        volume.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
        });

        Line line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(100);
        line2.getStyleClass().add("progressLine");

        StackPane volumePane = new StackPane();
        volumePane.getChildren().addAll(volume,pb2,line2);
        volumePane.setAlignment(Pos.CENTER_LEFT);

        Text time = new Text("01:35 / 3:75");
        time.getStyleClass().add("secondarytext");
        //time.setFill(Color.rgb(116, 204, 219, 1));

        Pane rightSide = new HBox();
        rightSide.getChildren().addAll(mute, volumePane, time);
        ((HBox) rightSide).setAlignment(Pos.BASELINE_RIGHT);
        rightSide.setPadding(new Insets(0, 45, 0, 0));

        //HINTERGRUND
        Rectangle progressBackground = new Rectangle();
        progressBackground.setId("progressBackground");
        progressBackground.setHeight(750);

        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });
        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculateBackgroundProgress(progress, progressBackground);
        });


        progressBackground.setWidth(1024/2);

        //REGIONS
        Pane region = new HBox();
        region.setPrefWidth(50);
        region.setMinWidth(50);
        HBox.setHgrow(region, Priority.ALWAYS);

        Pane region2 = new HBox();
        region2.setPrefWidth(50);
        region2.setMinWidth(50);
        HBox.setHgrow(region2, Priority.ALWAYS);


        bot.getChildren().addAll(songInfo, region2, controlButtons, region, rightSide);
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
