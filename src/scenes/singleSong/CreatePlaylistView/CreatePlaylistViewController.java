package scenes.singleSong.CreatePlaylistView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.Playlist;
import Controller.Track;
import Exceptions.keinSongException;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import scenes.singleSong.SelectMainView;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.actPlaylistView.ActPlaylistViewMobile;
import scenes.singleSong.actPlaylistView.TrackCell;
import scenes.singleSong.observView.ObservView;

public class CreatePlaylistViewController {

    Button shuffle, repeat;
    MP3Player player;

    CreatePlaylistView view;
    CreatePlaylistViewMobile viewMobile;
    Text actPlaylistTitle;
    HBox data;
    ObservableList<Track> list;


    public CreatePlaylistViewController(ObservView observView, MP3Player player, SelectMainView select){

        view = new CreatePlaylistView(observView, player);
        viewMobile = new CreatePlaylistViewMobile(observView, player);
        this.player = player;
        view.getStyleClass().add("scrolling");
        view.getStylesheets().add(getClass().getResource("contentStyle.css").toExternalForm());
        viewMobile.getStyleClass().add("scrolling");
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewMobile.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);




        initialize();
    }



    public void initialize(){


    }

    public CreatePlaylistView getView(){
        return view;
    }



    public void calcDataWidth(double x){
        actPlaylistTitle.setWrappingWidth((x-80)*0.45);
        data.setPrefWidth((x-80)*0.45);
    }


}
