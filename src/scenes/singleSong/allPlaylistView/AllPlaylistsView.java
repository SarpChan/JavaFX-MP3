package scenes.singleSong.allPlaylistView;

import Controller.Playlist;
import Controller.PlaylistManager;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import scenes.singleSong.observView.ObservView;
import scenes.singleSong.observView.Views;

public class AllPlaylistsView extends ScrollPane {
    protected final int LIST_CELL_HEIGHT = 50;
    protected ListView <Playlist> allPlaylists, suggestedPlaylists;

    protected Label bibliothekenTxt,  suggestedPlayliststTxt;
    protected Button neueKompiliertePlaylist;
    protected VBox all, buttons;
    protected Region region;

    /** Constructor
     *
     */
    public AllPlaylistsView(ObservView observView){
        all = new VBox();
        allPlaylists = new ListView<>();
        suggestedPlaylists = new ListView<>();

        buttons = new VBox(10);
        region = new Region();
        region.setPrefHeight(40);

        //FALLS NOCH KEINE VORGESCHLAGENEN PLAYLISTEN ERSTELLT WURDEN, WERDEN SIE HIER ERSTELLT
        if(!PlaylistManager.getSuggestedPlaylists().isEmpty()) {
            suggestedPlaylists.setItems(PlaylistManager.getSuggestedPlaylists());
        }

        allPlaylists.setItems(PlaylistManager.getPlaylistArrayList());

        //BUTTONS UND LABELS WERDEN ERSTELLT UND BEKOMMEN CSS-TAGS ZUGEWIESEN
        bibliothekenTxt = new Label(("Bibliotheken").toUpperCase());

        neueKompiliertePlaylist = new Button("Neue kompilierte Playlist ");
        suggestedPlayliststTxt = new Label(("Playlisten für dich ").toUpperCase());

        bibliothekenTxt.setTextAlignment(TextAlignment.LEFT);

        suggestedPlayliststTxt.setTextAlignment(TextAlignment.LEFT);

        bibliothekenTxt.getStyleClass().add("headline");
        neueKompiliertePlaylist.getStyleClass().add("create");

        suggestedPlayliststTxt.getStyleClass().add("headline");

        //EVENTHANDLER & LISTENER WERDEN ANGEMELDET
        neueKompiliertePlaylist.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            observView.switchView(Views.CREATEVIEWDESKTOP);
            allPlaylists.getSelectionModel().clearSelection();
            suggestedPlaylists.getSelectionModel().clearSelection();

        });

        allPlaylists.getStyleClass().add("scrollbar");
        allPlaylists.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        allPlaylists.prefHeightProperty().bind(Bindings.size(PlaylistManager.getPlaylistArrayList()).multiply(LIST_CELL_HEIGHT));

        suggestedPlaylists.getStyleClass().add("scrollbar");
        suggestedPlaylists.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        suggestedPlaylists.prefHeightProperty().bind(Bindings.size(PlaylistManager.getSuggestedPlaylists()).multiply(LIST_CELL_HEIGHT));

        PlaylistManager.getPlaylistArrayList().addListener((ListChangeListener)observable -> {

            allPlaylists.setItems(PlaylistManager.getPlaylistArrayList());

        });
        PlaylistManager.getSuggestedPlaylists().addListener((ListChangeListener) observable -> {
            suggestedPlaylists.setItems(PlaylistManager.getSuggestedPlaylists());
        });

        allPlaylists.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            observView.getAktPlaylistViewWeb().setActPlaylist(allPlaylists.getSelectionModel().getSelectedItem());
            observView.getAktPlaylistViewWeb().updatePlaylistInfo(allPlaylists.getSelectionModel().getSelectedItem());

            if(!suggestedPlaylists.getSelectionModel().isEmpty()) {
                suggestedPlaylists.getSelectionModel().clearSelection();
            }
            observView.switchView(Views.ACTPLAYLISTDESKTOP);
        } );

        suggestedPlaylists.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            observView.getAktPlaylistViewWeb().setActPlaylist(suggestedPlaylists.getSelectionModel().getSelectedItem());
            observView.getAktPlaylistViewWeb().updatePlaylistInfo(suggestedPlaylists.getSelectionModel().getSelectedItem());
            observView.switchView(Views.ACTPLAYLISTDESKTOP);
            if(!allPlaylists.getSelectionModel().isEmpty()) {
                allPlaylists.getSelectionModel().clearSelection();
            }
        });

        //BOXEN WERDEN ZUGEWIESEN
        buttons.getChildren().addAll(neueKompiliertePlaylist);
        all.getChildren().addAll(bibliothekenTxt, allPlaylists, buttons, region, suggestedPlayliststTxt, suggestedPlaylists);
        this.setContent(all);
        this.getStyleClass().add("allPlaylistsView");
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(0, 0, 0, 10));

    }
}
