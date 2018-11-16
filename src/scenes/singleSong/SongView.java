package scenes.singleSong;

import Controller.keinSongException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SongView implements EventHandler<ActionEvent> {

    public Scene buildScene() {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));


        Button play = new Button();
        play.getStyleClass().add("icon-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play2") + "\";");
        play.setPickOnBounds(true);
        play.setOnAction(this);
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {new Thread(()-> {

                try {
                    Controller.Steuerung2.control();


                } catch (keinSongException e) {
                    e.printStackTrace();
                }

        }).start();

        });


        Button previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");
        previous.setPickOnBounds(true);

        Button next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");
        next.setPickOnBounds(true);

        Button pause = new Button();
        pause.getStyleClass().add("icon-button");
        pause.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
        pause.setPickOnBounds(true);

        Button volume = new Button();
        volume.getStyleClass().add("icon-button");
        volume.setStyle("-fx-shape: \"" + getPathFromSVG("volume") + "\";");
        volume.setPickOnBounds(true);

        Button repeater = new Button();
        repeater.getStyleClass().add("icon-button");
        repeater.setStyle("-fx-shape: \"" + getPathFromSVG("repeat") + "\";");
        repeater.setPickOnBounds(true);

        Pane bot = new HBox(8);
        bot.setPadding(new Insets(2, 0, 10, 0));
        ((HBox) bot).setAlignment(Pos.CENTER);
        bot.getChildren().addAll(volume, previous, play, next, repeater);

        root.setBottom(bot);

        Scene x = new Scene(root, 375, 568);
        x.getStylesheets().add("scenes/singleSong/stylesheet.css");
        return x;
    }

    private static String getPathFromSVG(String filename){
        String d = "abc";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder =  factory.newDocumentBuilder();
            Document doc = builder.parse("src/resources/icons/"+ filename+".svg");
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

    @Override
    public void handle(ActionEvent event) {

    }
}
