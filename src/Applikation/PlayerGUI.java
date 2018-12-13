package Applikation;

import Controller.MP3Player;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scenes.singleSong.AllArtistsView;

import java.util.HashMap;


public class PlayerGUI extends Application {
    MP3Player player;
    Stage main;
    HashMap<String,Scene > szenen;

    @Override
    public void init(){
        player = new MP3Player();



    }


    @Override
    public void start(Stage primaryStage) {

        main = primaryStage;

        szenen = new HashMap<>();
        szenen.put("01", new scenes.singleSong.SongView().buildScene(this, player));
       // szenen.put("02", new scenes.MikeView.MikeView().buildScene(this,player));
        szenen.put("03", new scenes.singleSong.MainView().buildScene(this, player));
        szenen.put("04", new AllArtistsView().buildScene(this, player));


        BorderPane root = new BorderPane();

        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(szenen.get("01"));

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }




    public void switchScene(PlayerGUI gui, String code){

        switch (code){
            case "01":
                main.setScene(new scenes.singleSong.MainView().buildScene(this, player));
                break;
        }
    }
}


