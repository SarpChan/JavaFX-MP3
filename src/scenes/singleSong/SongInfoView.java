package scenes.singleSong;

import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SongInfoView extends VBox {

    Button back;
    ImageView cover;
    Text title, artist, album, length;


    public SongInfoView(MP3Player player){

        back = new Button("zurück");



        HBox basicSongInfo = new HBox(50);

        cover = new ImageView();
        cover.setFitHeight(150);
        cover.setFitWidth(150);
        cover.setImage(player.getAlbumImage());

        HBox titleBox = new HBox();

        title = new Text(player.getTrack());
        title.setStyle("-fx-fill:#fff; -fx-font-size: 35px;");

        titleBox.getChildren().addAll(title);
        titleBox.setPadding(new Insets(0,0,6,0));


        artist = new Text(player.getSongArtist());
        artist.setStyle("-fx-fill:#bbb;");

        album = new Text(player.getAlbum());
        album.setStyle("-fx-fill:#bbb;");
        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        length = new Text("" + zeitanzeige.format(player.getSongLength()));
        length.setStyle("-fx-fill:#bbb;");


        VBox songNames = new VBox(3);
        songNames.getChildren().addAll(titleBox, artist, album, length);


        basicSongInfo.getChildren().addAll(cover, songNames);





        this.getChildren().addAll(back, basicSongInfo);

    }




}
