package scenes.singleSong.actPlaylistView;

import Controller.MP3Player;
import Controller.Playlist;
import Controller.Track;
import Exceptions.keinSongException;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.observView.ObservView;

public class ActPlaylistViewController {

    Button shuffle, repeat;
    MP3Player player;
    ListView trackListView;
    Playlist aktPlaylist;
    ActPlaylistView view;
    ActPlaylistViewMobile viewMobile;
    Text actPlaylistTitle;
    HBox data;
    ObservableList<Track> list;
    boolean mobileActive;

    /** Constructor
     *
     */
    public ActPlaylistViewController(ObservView observView, MP3Player player, SelectMainView select){

        view = new ActPlaylistView(observView, player);
        viewMobile = new ActPlaylistViewMobile(observView, player);
        this.player = player;

        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewMobile.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        view.getStyleClass().add("scrolling");
        view.getStylesheets().add(getClass().getResource("contentStyle.css").toExternalForm());
        viewMobile.getStyleClass().add("scrolling");

        switch(select){
            case DESKTOP:
                shuffle = view.shuffle;
                repeat = view.repeat;
                trackListView = view.trackListView;
                aktPlaylist = view.actPlaylist;
                actPlaylistTitle = view.actPlaylistTitle;
                data = view.data;
                list = view.list;
                mobileActive = false;
                break;
            case MOBILE:
                shuffle = viewMobile.shuffle;
                repeat = viewMobile.repeat;
                trackListView = viewMobile.trackListView;
                aktPlaylist = viewMobile.actPlaylist;
                actPlaylistTitle = viewMobile.actPlaylistTitle;
                data = viewMobile.data;
                list = viewMobile.list;
                mobileActive = true;
                break;
        }
        initialize();
    }


    /** Die CellFactory für die grafische Ausgabe der aktuell ausgewählten Playlist wird angestoßen sowie EventHandler angemeldet.
     */
    public void initialize(){
        trackListView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
            @Override
            public ListCell<Track> call(ListView<Track> param) {
                if (mobileActive == false){
                    return new TrackCell();
                }
                else {
                    return new TrackCellMobile();
                }
            }
        });

        shuffle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.changeShuffle();
            if(shuffle.getStyleClass().contains("notActive-button")){

                shuffle.getStyleClass().removeAll("notActive-button");
                shuffle.getStyleClass().add("active-button");
            } else{
                shuffle.getStyleClass().removeAll("active-button");
                shuffle.getStyleClass().add("notActive-button");
            }
        });

        repeat.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.changeRepeat();
            if(repeat.getStyleClass().contains("notActive-button")){

                repeat.getStyleClass().removeAll("notActive-button");
                repeat.getStyleClass().add("active-button");
            } else{
                repeat.getStyleClass().removeAll("active-button");
                repeat.getStyleClass().add("notActive-button");
            }
        });

        trackListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.play((Track)trackListView.getSelectionModel().getSelectedItem(), aktPlaylist);
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });

        player.songProperty().addListener((observable, oldValue, newValue) -> {
            trackListView.getSelectionModel().clearSelection();
            trackListView.getSelectionModel().select(newValue);

        });
    }

    /** Getter um die view der Desktop-Variante zu laden
     */
    public ActPlaylistView getView(){
        return view;
    }
    /** Getter um die view der Mobile-Variante zu laden
     */
    public ActPlaylistViewMobile getViewMobile(){
        return viewMobile;
    }


/*
    public void calcDataWidth(double x){
        actPlaylistTitle.setWrappingWidth((x-80)*0.45);
        data.setPrefWidth((x-80)*0.45);
    }

    public void setAktPlaylist(Playlist playlist ){
        list.addAll(playlist.getTracks());
        list.removeAll(aktPlaylist.getTracks());
        aktPlaylist = playlist;
        trackListView.setItems(list);

    }
*/

}
