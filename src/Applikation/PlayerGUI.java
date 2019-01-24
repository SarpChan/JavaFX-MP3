package Applikation;

import Controller.MP3Player;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.observView.ObservMobile;
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
        //main.setMinWidth(550);
        main.setMinHeight(150);

        szenen = new HashMap<>();


       szenen.put("01", new ObservView().buildScene(this, player));
       szenen.put("02", new ObservMobile().buildScene(this, player));
       szenen.put("03", new ObservView().buildScene(this, player));





        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(szenen.get("01"));

        primaryStage.show();

        main.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() <= 550){
                switchView(SelectMainView.MOBILE);
            }else{
                switchView(SelectMainView.DESKTOP);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }




    public void switchView(SelectMainView select){

        switch (select){
            case DESKTOP:
               // szenen.get("01").setRoot(szenen.get("01").getRoot());
                main.setScene(szenen.get("01"));

                //main.setScene(new scenes.singleSong.MainView().buildScene(this, player));
                break;
            case MOBILE:
                //szenen.get("01").setRoot(szenen.get("02").getRoot());
                main.setScene(szenen.get("02"));
                break;
        }
    }

    public static double getStageWidth(){
        return main.getWidth();
    }


}


