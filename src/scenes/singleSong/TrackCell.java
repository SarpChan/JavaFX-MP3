package scenes.singleSong;


import Controller.Track;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TrackCell extends javafx.scene.control.ListCell<Controller.Track> {

    private Pane trackPane;
    private Label title;
    private Label album;
    private Label artist;
    private Label songlaenge;
    private Pane root;
    private Line seperator;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");


    public TrackCell() {

        VBox infoPane = new VBox();
        title = new Label();
        artist = new Label();
        seperator = new Line();
        seperator.prefWidth(this.getMaxWidth());
        seperator.setStartX(0);
        seperator.setStartY(0);
        seperator.setEndX(this.getMaxWidth());
        seperator.setEndY(0);
        seperator.setStroke(Color.RED);
        title.getStyleClass().add("label");
        artist.getStyleClass().add("label");
        infoPane.getChildren().addAll(title, artist,seperator);
        infoPane.getStylesheets().add(getClass().getResource("liststyle.css").toExternalForm());

        root = new HBox();

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


            this.setGraphic(root);
        } else {
            this.setGraphic(null);
        }
    }
}
