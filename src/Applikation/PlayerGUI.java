package Applikation;

import Controller.MP3Player;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scenes.singleSong.SelectMainView;

import scenes.singleSong.observView.ObservView;
import scenes.singleSong.observView.Views;


import java.util.HashMap;

public class PlayerGUI extends Application {
    private MP3Player player;
    private static Stage main;
    private HashMap<String,Scene > szenen;
    private ObservView observView;

    /** Main-Methode.
     *
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** Initialisiert den MP3-Player.
     *
     */
    @Override
    public void init(){
        player = new MP3Player();
    }

    /** Startet die grafische Anzeige des MP3-Players.
     *
     */
    @Override
    public void start(Stage primaryStage) {
        main = primaryStage;
        //main.setMinWidth(550);
        main.setMinHeight(150);
        main.setMinWidth(400);
        szenen = new HashMap<>();

        observView = new ObservView();
        szenen.put("01", observView.buildScene(this, player));
        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(szenen.get("01"));

        primaryStage.show();
    }
}


