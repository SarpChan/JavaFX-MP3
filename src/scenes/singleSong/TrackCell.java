package scenes.singleSong;


import Controller.Track;
import javafx.beans.binding.Binding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.beans.binding.Bindings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TrackCell extends javafx.scene.control.ListCell<Controller.Track> {

    private Pane trackPane;
    private Label title;
    private Label album;
    private Label artist;
    private Label songlength;
    private static Pane root;
    private Line seperator;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    private static GridPane infoPane;


    public TrackCell() {

        infoPane = new GridPane();
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
        infoPane.getColumnConstraints().addAll(titleColumn, artistColumn, albumColumn, songlengthColumn);
        infoPane.prefWidthProperty().bind(this.widthProperty());

            infoPane.add(title, 0, 0);
            infoPane.add(artist, 1, 0);
            infoPane.add(album, 2, 0);
            infoPane.add(songlength, 3, 0);

        //infoPane.getChildren().addAll(title, artist,seperator);
        infoPane.getStylesheets().add(getClass().getResource("liststyle.css").toExternalForm());
        infoPane.setVgap(5);


        this.prefWidthProperty().bind(ActPlaylistView.getPlaylistWidth().subtract(2));
        this.setMaxWidth(this.getPrefWidth());


        root = new Pane();
        root.getChildren().addAll(infoPane);
        root.getStyleClass().add("list-cell");
        root.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());


    }



    protected void updateItem(Track p, boolean empty) {
        super.updateItem(p, empty);



        if(!empty) {
            title.setText(p.getTitle());
            artist.setText(p.getArtist());
            album.setText(p.getAlbum());
            songlength.setText(zeitanzeige.format(p.getSonglength()));
            this.setGraphic(root);
        } else {
            this.setGraphic(null);
        }
    }




}
