package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import controlElements.Progress;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ObservView {

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        GridPane root = new GridPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        AllPlaylistsView left = new AllPlaylistsView();
        ActPlaylistView center = new ActPlaylistView(player);
        center.setAlignment(Pos.CENTER_RIGHT);
        Progress bottom = new Progress();

        root.add(left, 0,0);
        root.add(center,1,0);
        root.add(bottom,0,1);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(20);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(80);
        root.getColumnConstraints().addAll(leftColumn, rightColumn);

        RowConstraints top = new RowConstraints();
        top.setPercentHeight(85);
        root.getRowConstraints().addAll(top);

        Scene observView = new Scene(root, 1024, 750);
        observView.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());

        return observView;
    }
}
