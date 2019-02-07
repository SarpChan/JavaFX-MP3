package scenes.singleSong.allPlaylistView;

import Controller.Playlist;
import Controller.PlaylistManager;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import scenes.singleSong.observView.ObservView;
import scenes.singleSong.observView.Views;

public class AllPlaylistsView extends ScrollPane {
   ListView <Playlist> allPlaylists;
   ObservableList<Playlist> list;
   Text bibliothekenTxt, playlistsTxt, suggestedPlayliststTxt;
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
        suggestedPlayliststTxt = new Text(("Kompilierte Playlist + ").toUpperCase());
        bibliothekenTxt.setTextAlignment(TextAlignment.LEFT);
        playlistsTxt.setTextAlignment(TextAlignment.LEFT);
        suggestedPlayliststTxt.setTextAlignment(TextAlignment.LEFT);
        bibliothekenTxt.getStyleClass().add("headline");
        playlistsTxt.getStyleClass().add("headline");
        suggestedPlayliststTxt.getStyleClass().add("headline");

        suggestedPlayliststTxt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            observView.switchView(Views.CREATEVIEW);

        });

        allPlaylists.getStyleClass().add("scrollbar");
        allPlaylists.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        allPlaylists.prefHeightProperty().bind(Bindings.size(PlaylistManager.getPlaylistArrayList()).multiply(LIST_CELL_HEIGHT));

        PlaylistManager.getPlaylistArrayList().addListener((ListChangeListener)observable -> {

            allPlaylists.setItems(PlaylistManager.getPlaylistArrayList());

        });



        allPlaylists.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            observView.getAktPlaylistViewWeb().setAktPlaylist(newValue);
            observView.getAktPlaylistViewWeb().updatePlaylistInfo(newValue);
        });


        all.getChildren().addAll(bibliothekenTxt, allPlaylists, playlistsTxt,suggestedPlayliststTxt);


        this.setContent(all);
        this.getStyleClass().add("allPlaylistsView");
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(0, 0, 0, 10));

    }
}
