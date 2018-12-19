package scenes.singleSong;

import scenes.singleSong.*;
import Applikation.PlayerGUI;
import Controller.MP3Player;
import controlElements.Progress;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ObservView {
    private static GridPane root;
    private Scene observView;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        root = new GridPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        AllPlaylistsView left = new AllPlaylistsView();
        ActPlaylistView center = new ActPlaylistView(player);
        Progress bottom = new Progress();

        root.add(left, 0,0);
        root.add(center,1,0);
        root.add(bottom,0,1);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(25);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(75);
        root.getColumnConstraints().addAll(leftColumn,rightColumn);


        RowConstraints top = new RowConstraints();
        top.setPercentHeight(85);
        root.getRowConstraints().addAll(top);



        observView = new Scene(root, 1024, 750);
        observView.widthProperty().addListener(e -> {
            ActPlaylistView.calcDataWidth(observView.getWidth());
        });
        observView.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());


        return observView;
    }

    public static double getRootWidth(){
        return root.getWidth();
    }

    public double getObserviewWidth(){
        return observView.getWidth();
    }
}
