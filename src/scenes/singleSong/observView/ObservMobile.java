package scenes.singleSong.observView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import scenes.singleSong.MainViewController;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.actPlaylistView.ActPlaylistViewController;
import scenes.singleSong.actPlaylistView.ActPlaylistViewMobile;
import scenes.singleSong.allPlaylistView.AllPlaylistsView;
import scenes.singleSong.singleSongView.SingleSongView;

public class ObservMobile {

    private StackPane root;
    private GridPane top;
    private VBox all;
    private Scene observView;
    private SingleSongView songCenter;
    private VBox bottom;
    private VBox region;
    private AllPlaylistsView left;
    private ActPlaylistViewController playlistCenter;
    private MainViewController singleSong;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        root = new StackPane();
        top = new GridPane();
        all = new VBox();
        observView = new Scene(root, 550, 750);
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

       // songCenter = new SingleSongView(this, player);
        singleSong = new MainViewController(player, SelectMainView.MOBILE);
        playlistCenter = new ActPlaylistViewController(gui, player, SelectMainView.MOBILE);
        bottom = singleSong.getView();
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        //left = new AllPlaylistsView();

        //top.add(left, 0,0);
        top.add(playlistCenter.getViewMobile(),1,0);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(30,0,0,0));
        //ColumnConstraints leftColumn = new ColumnConstraints();
        //leftColumn.setPercentWidth(25);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(100);
        top.getColumnConstraints().addAll(rightColumn);

        region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);


        HBox playlist = new HBox();
        HBox regionForPlaylist1 = new HBox();
        HBox regionForPlaylist2 = new HBox();
        HBox.setHgrow(regionForPlaylist1, Priority.ALWAYS);
        HBox.setHgrow(regionForPlaylist2, Priority.ALWAYS);

        playlist.getChildren().addAll(playlistCenter.getViewMobile());
        playlist.setAlignment(Pos.CENTER);
        playlist.setPadding(new Insets(25, 0, 0, 0));

        all.getChildren().addAll(playlist,region,bottom);
        root.getChildren().addAll(all);

        observView.widthProperty().addListener(e -> {
            playlistCenter.calcDataWidth(observView.getWidth());
            //songCenter.setImgWidth(observView.getWidth());

        });
        observView.getStylesheets().add(getClass().
                getResource("progressBarStyle.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("contentStyle.css").toExternalForm());

        return observView;
    }

    public double getRootWidth(){
        return observView.getWidth();
    }
    public double getRootHeight(){
        return observView.getHeight();
    }

    public double getObserviewWidth(){
        return observView.getWidth();
    }

    public void switchView(){
        all.getChildren().remove(top);
        root.getChildren().add(0,songCenter);
    }

    public void changePlayButton(){
        singleSong.changePlayButton();
    }

    /*public ActPlaylistView getPlaylistCenter() {
        return playlistCenter;
    }*/

    public StackPane getRoot() {
        return root;
    }
}
