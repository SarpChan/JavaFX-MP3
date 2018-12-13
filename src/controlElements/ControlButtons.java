package controlElements;


import Controller.MP3Player;
import Exceptions.keinSongException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class ControlButtons extends ExtendedHBox {

    Button previous, play, next;
    MP3Player player;

    public ControlButtons(MP3Player player){

        this.player = player;

        this.setSpacing(20);

        play = new Button();
        play.getStyleClass().add("play-button");
        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
        play.setPadding(new Insets(0, 100, 0, 100));
        play.setOnAction(playButtonOnAction());

        previous = new Button();
        previous.getStyleClass().add("icon-button");
        previous.setStyle("-fx-shape: \"" + getPathFromSVG("previous") + "\";");
        previous.setOnAction(prevButtonOnAction());

        next = new Button();
        next.getStyleClass().add("icon-button");
        next.setStyle("-fx-shape: \"" + getPathFromSVG("next") + "\";");
        next.setOnAction(nextButtonOnAction());

        this.getChildren().addAll(previous, play, next);
        this.setAlignment(Pos.CENTER);
    }

    private EventHandler<ActionEvent> playButtonOnAction(){
        return new EventHandler <ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    if (player.isInitialized()) {
                        if (!player.isPlaying()) {
                            player.play();
                            play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");

                        } else {
                            player.pause();
                            play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");

                        }
                    } else {
                        player.play();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");

                    }

                } catch (keinSongException e) {
                    e.printStackTrace();
                }

            }

        };
    }

    private EventHandler<ActionEvent> nextButtonOnAction(){
        return new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0){
                try{
                    player.next();
                }catch(keinSongException e){
                    e.printStackTrace();
                }
            }

        };
    }

    private EventHandler<ActionEvent> prevButtonOnAction(){
        return new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0){
                try{
                    player.previous();
                }catch(keinSongException e){
                    e.printStackTrace();
                }
            }

        };


    }

}
