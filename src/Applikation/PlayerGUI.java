package Applikation;

import Controller.MP3Player;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scenes.singleSong.observView.ObservView;


import java.util.HashMap;


public class PlayerGUI extends Application {
    MP3Player player;
    static Stage main;
    HashMap<String,Scene > szenen;

    @Override
    public void init(){
        player = new MP3Player();

    }


    @Override
    public void start(Stage primaryStage) {

        main = primaryStage;
        main.setMinWidth(550);
        main.setMinHeight(150);

        szenen = new HashMap<>();


       szenen.put("01", new ObservView().buildScene(this, player));





        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(szenen.get("04"));

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }




    public void switchScene(PlayerGUI gui, String code){

        switch (code){
            case "04":
                //main.setScene(new scenes.singleSong.MainView().buildScene(this, player));
                break;
        }
    }

    public static double getStageWidth(){
        return main.getWidth();
    }


}


