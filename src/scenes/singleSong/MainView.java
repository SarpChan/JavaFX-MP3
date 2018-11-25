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
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

        Pane song = new VBox();
       // song.setPrefHeight(300);
        //song.setMinHeight(300);

        Pane bot = new HBox(8);
        //bot.setPrefHeight(20);
        bot.setPadding(new Insets(25, 0, 25, 0));
        //((VBox) song).setSpacing(20);
        ((VBox) song).setAlignment(Pos.CENTER);

        //((HBox) bot).setAlignment(Pos.CENTER);

        Button play = new Button("play");
        Button previous = new Button("prev");
        Button next = new Button("next");

        Slider progress = new Slider();
        progress.setMin(0);
        progress.setMax(100);

        Pane songInfo = new HBox();
        Text title = new Text("TRACK" + " ");
        Text interpret = new Text("Arctic Monkeys");
        songInfo.getChildren().addAll(title, interpret);
        ((HBox) songInfo).setAlignment(Pos.CENTER_LEFT);
        songInfo.setPadding(new Insets(0, 0, 0, 45));

        Pane controlButtons = new HBox();
        controlButtons.getChildren().addAll(previous, play, next);
        ((HBox) controlButtons).setAlignment(Pos.CENTER);

        title.setFill(Color.rgb(116, 204, 219, 1));
        interpret.setFill(Color.rgb(85, 129, 137, 1));

        Button mute = new Button("mute");
        Slider volume = new Slider();
        volume.setMin(0);
        volume.setMax(1);
        volume.setOrientation(Orientation.HORIZONTAL);

        Text time = new Text("01:35/3:75");

        Pane region = new HBox();
        region.setPrefWidth(50);
        region.setMinWidth(50);
        HBox.setHgrow(region, Priority.ALWAYS);

        Pane region2 = new HBox();
        region2.setPrefWidth(50);
        region2.setMinWidth(50);
        HBox.setHgrow(region2, Priority.ALWAYS);


        Pane rightSide = new HBox();
        rightSide.getChildren().addAll(mute, volume, time);
        ((HBox) rightSide).setAlignment(Pos.BASELINE_RIGHT);
        rightSide.setPadding(new Insets(0, 45, 0, 0));

        time.setFill(Color.rgb(116, 204, 219, 1));

        bot.getChildren().addAll(songInfo, region2, controlButtons, region, rightSide);

        song.getChildren().addAll(progress, bot);

        root.setBottom(song);

        Scene x = new Scene(root, 1024, 750);
        x.getStylesheets().add("scenes/singleSong/stylesheet.css");
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
}
