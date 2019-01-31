package scenes.singleSong;

import Controller.MP3Player;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SongInfoController {

    SongInfoView view;
    ImageView cover;
    Text title, artist, length, album;

    public SongInfoController(MP3Player player){
        view = new SongInfoView(player);
        cover = view.cover;
        title = view.title;
        artist = view.artist;
        album = view.album;
        length = view.length;
        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");

        player.songProperty().addListener((observable, oldValue, newValue) -> {
            cover.setImage(player.getAlbumImage());
            title.setText(player.getTrack());
            artist.setText(player.getSongArtist());
            album.setText(player.getAlbum());
            length.setText(zeitanzeige.format(player.getSongLength()));
        });

    }

    public VBox getView(){
        return view;
    }
}
