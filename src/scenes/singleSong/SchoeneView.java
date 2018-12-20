package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class SchoeneView{
    StackPane root;
    ImageView actImg;
    int scale = 50;

    public Scene buildScene(MP3Player player) {
        root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(new Color(1,  1, 1, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene schoeneView = new Scene(root, 1024, 750);

        MainView bottom = new MainView(player);
        actImg = new ImageView();

        actImg.setImage(player.getAlbumImage());
        actImg.setEffect(new GaussianBlur());
        actImg.setFitWidth(schoeneView.getWidth()+scale);
        actImg.setFitHeight(root.getHeight()+scale);

        bottom.setAlignment(Pos.BOTTOM_CENTER);
        VBox region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);



        root.getChildren().addAll(actImg);


        schoeneView.widthProperty().addListener(e -> {
            actImg.setFitWidth(schoeneView.getWidth()+scale);
            actImg.setFitHeight(schoeneView.getHeight()+scale);
        });

        schoeneView.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        schoeneView.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());

        return schoeneView;

    }

}
