package scenes.singleSong;

import Controller.MP3Player;
import Controller.PlaylistManager;
import Exceptions.keinSongException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.MikeView.MikeView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SongView {

    public Scene buildScene() {

        MP3Player player = new MP3Player();

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));



        Button play = new Button();
        play.setText(getFirstSongFromPlaylist("Test.m3u"));
        play.getStyleClass().add("icon-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play2") + "\";");
        play.setPickOnBounds(true);
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.play(play.getText());
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });


        Button pause = new Button();
        pause.getStyleClass().add("icon-button");
        pause.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
        pause.setPickOnBounds(true);
        pause.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.pause();
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });


        Button previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");
        previous.setPickOnBounds(true);

        Button next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");
        next.setPickOnBounds(true);
        next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.next();
            }  catch (keinSongException e) {
                e.printStackTrace();
            }
        });

        Button volume = new Button();
        volume.getStyleClass().add("icon-button");
        volume.setStyle("-fx-shape: \"" + getPathFromSVG("volume") + "\";");
        volume.setPickOnBounds(true);


        Button search = new Button();
        search.getStyleClass().add("icon-button");
        search.setStyle("-fx-shape: \"" + getPathFromSVG("createPlaylist") + "\";");
        search.setPickOnBounds(true);
        search.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                PlaylistManager.savePlaylist(PlaylistManager.getAllTracks(), "Test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button repeater = new Button();
        repeater.getStyleClass().add("icon-button");
        repeater.setStyle("-fx-shape: \"" + getPathFromSVG("repeat") + "\";");
        repeater.setPickOnBounds(true);

        Pane bot = new HBox(8);
        bot.setPadding(new Insets(2, 0, 10, 0));
        ((HBox) bot).setAlignment(Pos.CENTER);
        bot.getChildren().addAll(search, volume, previous, play, pause, next, repeater);

        root.setBottom(bot);

        Scene x = new Scene(root, 375, 568);
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


}
