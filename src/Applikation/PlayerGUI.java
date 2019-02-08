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
    MP3Player player;
    static Stage main;
    HashMap<String,Scene > szenen;
    private ObservView observView;

    @Override
    public void init(){
        player = new MP3Player();

    }


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


    public static void main(String[] args) {
        launch(args);
    }
    public static double getStageWidth(){
        return main.getWidth();
    }


}


