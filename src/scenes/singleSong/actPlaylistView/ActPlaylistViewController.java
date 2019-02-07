package scenes.singleSong.actPlaylistView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.Playlist;
import Controller.Track;
import Exceptions.keinSongException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.observView.ObservView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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


    public ActPlaylistViewController(ObservView observView, MP3Player player, SelectMainView select){

        view = new ActPlaylistView(observView, player);
        viewMobile = new ActPlaylistViewMobile(observView, player);
        this.player = player;
        view.getStyleClass().add("scrolling");
        view.getStylesheets().add(getClass().getResource("contentStyle.css").toExternalForm());
        viewMobile.getStyleClass().add("scrolling");
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewMobile.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        switch(select){
            case DESKTOP:

                shuffle = view.shuffle;
                repeat = view.repeat;
                trackListView = view.trackListView;
                aktPlaylist = view.aktPlaylist;
                actPlaylistTitle = view.actPlaylistTitle;
                data = view.data;
                list = view.list;
                break;
            case MOBILE:
                shuffle = viewMobile.shuffle;
                repeat = viewMobile.repeat;
                trackListView = viewMobile.trackListView;
                aktPlaylist = viewMobile.aktPlaylist;
                actPlaylistTitle = viewMobile.actPlaylistTitle;
                data = viewMobile.data;
                list = viewMobile.list;
                break;

        }

        initialize();
    }



    public void initialize(){

        trackListView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {

            @Override
            public ListCell<Track> call(ListView<Track> param) {

                return new TrackCell();
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
    }

    public ActPlaylistView getView(){
        return view;
    }

    public ActPlaylistViewMobile getViewMobile(){
        return viewMobile;
    }

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
}
