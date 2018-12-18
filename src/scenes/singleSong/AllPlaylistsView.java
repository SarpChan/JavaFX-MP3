package scenes.singleSong;

import Controller.Playlist;
import Controller.PlaylistManager;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.text.TextAlignment;


import javafx.scene.layout.VBox;

public class AllPlaylistsView extends VBox {
   ListView <Playlist> allPlaylists;
   ObservableList<Playlist> list;
   Text bibliothekenTxt, playlistsTxt;
   final int LIST_CELL_HEIGHT = 50;

    public AllPlaylistsView(){
        allPlaylists = new ListView<>();
        list = FXCollections.observableArrayList();
        list.addAll(PlaylistManager.getAllPlaylists());

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


        allPlaylists.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        allPlaylists.prefHeightProperty().bind(Bindings.size(list).multiply(LIST_CELL_HEIGHT));
        allPlaylists.setMaxWidth(200);
        allPlaylists.setMaxHeight(400);
        this.getChildren().addAll(bibliothekenTxt, allPlaylists, playlistsTxt);
        this.setPadding(new Insets(60, 0, 60, 30));

    }
}
