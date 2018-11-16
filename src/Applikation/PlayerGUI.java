package Applikation;

import de.hsrm.mi.prog.util.StaticScanner;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.stage.Stage;



public class PlayerGUI extends Application {



    @Override
    public void start(Stage primaryStage){



        scenes.singleSong.SongView a = new scenes.singleSong.SongView();
        scenes.MikeView.MikeView b = new scenes.MikeView.MikeView();
        Scene scene;
        System.out.println("Möchtest du [m]ikes View oder [s]arps View?");
        switch (StaticScanner.nextString().toLowerCase()){
            case "m":  scene = b.buildScene();
            break;
            case "s": scene = a.buildScene();
            break;
            default: scene = a.buildScene();
        }


        primaryStage.setTitle("Coolste Gruppe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }





}
