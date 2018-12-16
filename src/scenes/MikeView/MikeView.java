package scenes.MikeView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Exceptions.keinSongException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class MikeView {

    Controller.MP3Player player = new Controller.MP3Player();
    PlayerGUI gui;

    public Scene buildScene(PlayerGUI gui, MP3Player player){
        this.player = player;
        this.gui = gui;

        Pane root = new VBox();
        root.setStyle("-fx-background-color: transparent;");
        root.setPadding(new Insets(20));




        Scene scene = new Scene(root, 300, 500);
        scene.setFill(Color.WHITE);

        root.getChildren().addAll(SongControl());

        return scene;



    }

    public void stop() {


    }

    private Pane SongInfo() {
        VBox container = new VBox(15);

        return container;

    }

    private Pane SongControl() {

        VBox container = new VBox(15);
        HBox buttons = new HBox();
        HBox slider = new HBox();

        Button next = new Button("NEXT");
        Button prev = new Button("PREV");
        Button pause = new Button("PAUSE");
        Button play = new Button("PLAY");

        play.setOnAction(onButtonPlayClick());
        pause.setOnAction(onButtonPauseClick());

        Slider volume = new Slider();

        volume.setPrefWidth(300);
        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(50);

        volume.valueProperty().addListener(changeValue());

        slider.getChildren().addAll(volume);
        slider.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(prev, play, pause, next);
        buttons.setAlignment(Pos.CENTER);

        container.getChildren().addAll(buttons, slider);

        return container;

    }

    private EventHandler<ActionEvent> onButtonPlayClick () {

        return new EventHandler <ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    player.play();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    private EventHandler<ActionEvent> onButtonPauseClick () {

        return new EventHandler <ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    player.pause();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    private ChangeListener<Number> changeValue(){



        return new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                player.volume((newValue.floatValue() / 100));
            }
        };
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
