package scenes.singleSong.actPlaylistView;


import Controller.Track;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TrackCellMobile extends javafx.scene.control.ListCell<Track> {

    private Label title;
    private Label album;
    private Label artist;
    private Label songlength;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    private GridPane root;

    /** Constructor
     *
     */
    public TrackCellMobile() {
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
        titleColumn.setPercentWidth(40);
        ColumnConstraints artistColumn = new ColumnConstraints();
        artistColumn.setPercentWidth(20);
        //ColumnConstraints albumColumn = new ColumnConstraints();
        //albumColumn.setPercentWidth(20);
        //ColumnConstraints songlengthColumn = new ColumnConstraints();
        //songlengthColumn.setPercentWidth(20);
        //songlengthColumn.setHalignment(HPos.RIGHT);
        root.getColumnConstraints().addAll(titleColumn, artistColumn);

        root.add(title, 0, 0);
        root.add(artist, 1, 0);
        root.prefWidthProperty().bind(this.widthProperty());
        root.setVgap(5);

        this.prefWidthProperty().bind(ActPlaylistView.getPlaylistWidth().subtract(2));
        this.setMaxWidth(this.getPrefWidth());
    }


    /** Verarbeitet das einzelne Listenelement und weist es den Inhalt den TrackCell Boxen zu.
     *
     */
    protected void updateItem(Track p, boolean empty) {
        super.updateItem(p, empty);
        if(p == null || empty) {
           setText(null);
           setGraphic(null);

        } else {
            title.setText(p.getTitle());
            artist.setText(p.getArtist());
            setGraphic(root);
        }
    }
}
