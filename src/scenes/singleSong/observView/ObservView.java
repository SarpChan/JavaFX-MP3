package scenes.singleSong.observView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import scenes.singleSong.MainView;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.allPlaylistView.AllPlaylistsView;
import scenes.singleSong.singleSongView.SingleSongView;

public class ObservView {
    private static StackPane root;
    private static GridPane top;
    private static VBox all;
    private static Scene observView;
    private static SingleSongView songCenter;
    private static MainView bottom;
    private static VBox region;
    private static AllPlaylistsView left;
    private static ActPlaylistView playlistCenter;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        root = new StackPane();
        top = new GridPane();
        all = new VBox();
        observView = new Scene(root, 1024, 750);
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        left = new AllPlaylistsView();
        playlistCenter = new ActPlaylistView(player);
        songCenter = new SingleSongView(player);
        bottom = new MainView(player);
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        top.add(left, 0,0);
        top.add(playlistCenter,1,0);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(30,0,0,0));
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(25);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(75);
        top.getColumnConstraints().addAll(leftColumn,rightColumn);

        region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);

        all.getChildren().addAll(top,region,bottom);
        root.getChildren().addAll(all);

        observView.widthProperty().addListener(e -> {
            ActPlaylistView.calcDataWidth(observView.getWidth());
            SingleSongView.setImgWidth(observView.getWidth());

        });
        observView.getStylesheets().add(getClass().
                getResource("progressBarStyle.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("contentStyle.css").toExternalForm());

        return observView;
    }

    public static double getRootWidth(){
        return observView.getWidth();
    }
    public static double getRootHeight(){
        return observView.getHeight();
    }

    public double getObserviewWidth(){
        return observView.getWidth();
    }

    public static void switchView(){
        all.getChildren().remove(top);
        root.getChildren().add(0,songCenter);
    }

}
