package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import controlElements.Progress;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ObservView {

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        AllPlaylistsView left = new AllPlaylistsView();
        ActPlaylistView center = new ActPlaylistView(player);
        Progress bottom = new Progress();

        root.setLeft(left);
        root.setCenter(center);
        root.setBottom(bottom);

        Scene observView = new Scene(root, 1024, 750);
        observView.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());

        return observView;
    }
}
