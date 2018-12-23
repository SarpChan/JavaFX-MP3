package scenes.singleSong.observView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import scenes.singleSong.MainView;
import scenes.singleSong.MainViewController;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.actPlaylistView.ActPlaylistViewController;
import scenes.singleSong.allPlaylistView.AllPlaylistsView;
import scenes.singleSong.singleSongView.SingleSongView;



public class ObservView {
    private StackPane root;
    private GridPane top;
    private VBox all;
    private Scene observView;
    private SingleSongView songCenter;
    private VBox bottom;
    private VBox region;
    private AllPlaylistsView left;
    private ActPlaylistView playlistCenter;
    private MainViewController singleSong;
    ActPlaylistViewController playlistControl;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        playlistControl = new ActPlaylistViewController(gui, player, SelectMainView.DESKTOP);
        root = new StackPane();
        top = new GridPane();
        all = new VBox();
        observView = new Scene(root, 1024, 750);
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        left = new AllPlaylistsView(this);

        songCenter = new SingleSongView(this, player);
        singleSong = new MainViewController(player, SelectMainView.DESKTOP);
        playlistCenter = new ActPlaylistViewController(gui, player, SelectMainView.DESKTOP).getView();
        bottom = singleSong.getView();
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        top.add(left, 0,0);
        top.add(playlistCenter,2,0);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(30,0,0,0));
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(20);
        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setPercentWidth(5);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(75);
        top.getColumnConstraints().addAll(leftColumn,centerColumn,rightColumn);

        region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);

        all.getChildren().addAll(top,region,bottom);
        root.getChildren().addAll(all);

        observView.widthProperty().addListener(e -> {
            playlistControl.calcDataWidth(observView.getWidth());
            songCenter.setImgWidth(observView.getWidth());

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

    public ActPlaylistView getPlaylistCenter() {
        return playlistCenter;
    }
}
