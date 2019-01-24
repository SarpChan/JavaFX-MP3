package scenes.singleSong.actPlaylistView;


import Controller.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TrackCell extends javafx.scene.control.ListCell<Controller.Track> {

    private Label title;
    private Label album;
    private Label artist;
    private Label songlength;
    //private static Pane root;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    private GridPane root;


    public TrackCell() {

        root = new GridPane();
        title = new Label();
        artist = new Label();
        album = new Label();
        songlength = new Label();

        title.getStyleClass().add("label");
        artist.getStyleClass().add("label");
        album.getStyleClass().add("label");
        songlength.getStyleClass().add("label");

        ColumnConstraints titleColumn = new ColumnConstraints();
        titleColumn.setPercentWidth(50);
        ColumnConstraints artistColumn = new ColumnConstraints();
        artistColumn.setPercentWidth(20);
        ColumnConstraints albumColumn = new ColumnConstraints();
        albumColumn.setPercentWidth(20);
        ColumnConstraints songlengthColumn = new ColumnConstraints();
        songlengthColumn.setPercentWidth(10);
        songlengthColumn.setHalignment(HPos.RIGHT);
        root.getColumnConstraints().addAll(titleColumn, artistColumn, albumColumn, songlengthColumn);
        root.prefWidthProperty().bind(this.widthProperty());
            root.add(title, 0, 0);
            root.add(artist, 1, 0);
            root.add(album, 2, 0);
            root.add(songlength, 3, 0);
        root.setVgap(5);

        this.prefWidthProperty().bind(ActPlaylistView.getPlaylistWidth().subtract(2));
        this.setMaxWidth(this.getPrefWidth());

    }



    protected void updateItem(Track p, boolean empty) {
        super.updateItem(p, empty);
        if(p == null || empty) {

           setText(null);
           setGraphic(null);

        } else {
            if(p.getTitle()==null || p.getTitle().equalsIgnoreCase("")){

            }
            title.setText(p.getTitle());
            artist.setText(p.getArtist());
            album.setText(p.getAlbum());
            songlength.setText(zeitanzeige.format(p.getSonglength()));

            setGraphic(root);

        }
    }

}
