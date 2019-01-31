package scenes.singleSong.allPlaylistView;

import Controller.Playlist;
import Controller.PlaylistManager;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.text.TextAlignment;


import javafx.scene.layout.VBox;


import javafx.scene.input.MouseEvent;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.observView.ObservView;

public class AllPlaylistsView extends ScrollPane {
   ListView <Playlist> allPlaylists;
   ObservableList<Playlist> list;
   Text bibliothekenTxt, playlistsTxt;
   final int LIST_CELL_HEIGHT = 50;
   VBox all;

    public AllPlaylistsView(ObservView observView){
        allPlaylists = new ListView<>();
        list = FXCollections.observableArrayList();
        list.addAll(PlaylistManager.getPlaylistArrayList());
        all = new VBox();

        for (Playlist playlist: list)
        {
            allPlaylists.getItems().add(playlist);
        }

        bibliothekenTxt = new Text(("Bibliotheken").toUpperCase());
        playlistsTxt = new Text(("Playlists +").toUpperCase());
        bibliothekenTxt.setTextAlignment(TextAlignment.LEFT);
        playlistsTxt.setTextAlignment(TextAlignment.LEFT);
        bibliothekenTxt.getStyleClass().add("headline");
        playlistsTxt.getStyleClass().add("headline");

        allPlaylists.getStyleClass().add("scrollbar");
        allPlaylists.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        allPlaylists.prefHeightProperty().bind(Bindings.size(list).multiply(LIST_CELL_HEIGHT));

        PlaylistManager.allPlaylistProperty().addListener((observable, oldValue, newValue) ->{
            list.clear();
            list.addAll(PlaylistManager.getPlaylistArrayList());
            for (Playlist playlist: list)
            {
                allPlaylists.getItems().add(playlist);
            }
        } );

        allPlaylists.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            observView.getPlaylistCenter().setAktPlaylist(newValue);
            observView.getPlaylistCenter().updatePlaylistInfo(newValue);
        });


        all.getChildren().addAll(bibliothekenTxt, allPlaylists, playlistsTxt);


        this.setContent(all);
        this.getStyleClass().add("allPlaylistsView");
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(0, 0, 0, 10));

    }
}
