package Applikation;

import Controller.MP3Player;
import de.hsrm.mi.prog.util.StaticScanner;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.stage.Stage;



public class PlayerGUI extends Application {
    MP3Player player;
    Stage main;

    @Override
    public void init(){
        player = new MP3Player();



    }


    @Override
    public void start(Stage primaryStage) {

        main = primaryStage;
            scenes.singleSong.SongView a = new scenes.singleSong.SongView();

            Scene scene = a.buildScene(this, player);


        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(scene);

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }




    public void switchScene(PlayerGUI bruder, int code){

        switch (code){
            case 01:
                main.setScene(new scenes.MikeView.MikeView().buildScene());
                break;
        }
    }
}


