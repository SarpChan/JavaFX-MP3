package scenes.singleSong;

import Applikation.PlayerGUI;
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
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.MikeView.MikeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SongView {

    boolean paused = true;
    public Scene buildScene(PlayerGUI gui, MP3Player player) {


        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        Pane bot = new HBox(8);
        bot.setPadding(new Insets(2, 0, 10, 0));
        ((HBox) bot).setAlignment(Pos.CENTER);

        ImageView img = new ImageView();
        img.setImage(new Image("/default.png"));
        img.setFitWidth(116);
        img.setFitHeight(110);

        Pane songInfo = new HBox();
        Text title = new Text(player.getTrack());
        Text interpret = new Text(player.getSongArtist());


        interpret.setStrikethrough(true);
        songInfo.getChildren().addAll(title, interpret);
        ((HBox) songInfo).setAlignment(Pos.CENTER_LEFT);
        songInfo.setPadding(new Insets(0, 0, 0, 45));
       //Buttons Anfang

        Button play = new Button();
        play.setText(getFirstSongFromPlaylist("Test.m3u"));
        play.getStyleClass().add("icon-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play2") + "\";");
        play.setPickOnBounds(true);
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if (paused) {
                    player.play(play.getText());
                    play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                    changePause();
                    img.setImage(player.getAlbumImage());

                } else{
                    player.pause();
                    play.setStyle("-fx-shape: \"" + getPathFromSVG("play2") + "\";");
                    changePause();
                }

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
                title.setText(player.getTrack());
                interpret.setText(player.getSongArtist());
                img.setImage(player.getAlbumImage());
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
        repeater.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gui.switchScene(gui, "01");

        });

        // Buttons Ende

        bot.getChildren().addAll(img,title, interpret, search, volume, previous, play, next, repeater);

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

    private void changePause(){
        paused = !paused;
    }

}
