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
    private static VBox root;
    private static GridPane control;
    private Scene observView;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        root = new VBox();
        control = new GridPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        AllPlaylistsView left = new AllPlaylistsView();
        ActPlaylistView center = new ActPlaylistView(player);
        MainView bottom = new MainView(player);

        control.add(left, 0,0);
        control.add(center,1,0);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(25);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(75);
        control.getColumnConstraints().addAll(leftColumn,rightColumn);

        RowConstraints top = new RowConstraints();
        //top.setPercentHeight(85);
        control.getRowConstraints().addAll(top);
        control.setAlignment(Pos.TOP_CENTER);
        control.setPadding(new Insets(30,0,0,30));
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        VBox region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);


        root.getChildren().addAll(control, region, bottom);


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
