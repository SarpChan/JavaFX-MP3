package scenes.singleSong.observView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import scenes.singleSong.*;
import scenes.singleSong.CreatePlaylistView.CreatePlaylistView;
import scenes.singleSong.CreatePlaylistView.CreatePlaylistViewController;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.actPlaylistView.ActPlaylistViewController;
import scenes.singleSong.actPlaylistView.ActPlaylistViewMobile;
import scenes.singleSong.allPlaylistView.AllPlaylistsView;


public class ObservView {
    private StackPane root;
    private GridPane top;
    private VBox all;
    private Scene observView;
    private VBox bottom;
    private VBox region;
    private AllPlaylistsView allePlaylistenView;
    private ActPlaylistView aktPlaylistViewWeb;
    private MainViewController mainViewController;
    private ActPlaylistViewController aktPlaylistController;
    private CreatePlaylistView createPlaylist;
    private MainViewController mainViewControllerMobile;
    private ActPlaylistViewMobile aktPlaylistViewMobile;
    private HBox playlist = new HBox();
    private VBox songInfo;
    private SongInfoController songInfoControl;
    private Views currentDesktop;
    private Views currentMobile;


    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        createPlaylist = new CreatePlaylistViewController(this, player, SelectMainView.DESKTOP).getView();
        aktPlaylistController = new ActPlaylistViewController(this, player, SelectMainView.DESKTOP);
        aktPlaylistViewWeb = aktPlaylistController.getView();
        aktPlaylistViewMobile= new ActPlaylistViewController(this, player, SelectMainView.MOBILE).getViewMobile();
        root = new StackPane();
        top = new GridPane();
        all = new VBox();
        observView = new Scene(root, 1024, 750);
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        currentDesktop = Views.ACTPLAYLISTDESKTOP;
        currentMobile = Views.ACTPLAYLISTMOBILE;
        allePlaylistenView = new AllPlaylistsView(this);


        mainViewController = new MainViewController(player, SelectMainView.DESKTOP, this);
        mainViewControllerMobile = new MainViewController(player, SelectMainView.MOBILE, this);

        songInfoControl = new SongInfoController(player, this);
        songInfo = songInfoControl.getView();

        bottom = mainViewController.getView();
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        top.add(allePlaylistenView, 0,0);
        top.add(aktPlaylistViewWeb,2,0);


        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(30,0,0,0));

        webColumns();

        region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);


        playlist.setAlignment(Pos.CENTER);


        all.getChildren().addAll(top,region,bottom);

        root.getChildren().addAll(all);

        observView.widthProperty().addListener(e -> {
            aktPlaylistController.calcDataWidth(observView.getWidth());


        });

        observView.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() <= 550){
                switchView(currentMobile);
            }else{
                switchView(currentDesktop);
            }
        });

        observView.getStylesheets().add(getClass().
                getResource("progressBarStyle.css").toExternalForm());
        observView.getStylesheets().add(getClass().
                getResource("contentStyle.css").toExternalForm());


        return observView;
    }

    public double getRootWidth(){
        return observView.getWidth();
    }
    public double getRootHeight(){
        return observView.getHeight();
    }

    public double getObserviewWidth(){
        return observView.getWidth();
    }

    public void switchView(Views view){
        songInfoControl.close();

        all.getChildren().clear();
        top.getColumnConstraints().clear();
        top.getChildren().clear();
        bottom = null;

        switch (view){
            case ACTPLAYLISTDESKTOP:

                webColumns();

                top.add(allePlaylistenView, 0,0);
                top.add(aktPlaylistViewWeb,2,0);
                bottom = mainViewController.getView();
                all.getChildren().addAll(top, region, bottom);
                currentDesktop = Views.ACTPLAYLISTDESKTOP;
                currentMobile = Views.ACTPLAYLISTMOBILE;

                break;

            case ACTPLAYLISTMOBILE:

                top.getChildren().addAll(aktPlaylistViewMobile);
                bottom = mainViewControllerMobile.getView();
                all.getChildren().addAll(top,region,bottom);
                currentDesktop = Views.ACTPLAYLISTDESKTOP;
                currentMobile = Views.ACTPLAYLISTMOBILE;
                break;

            case CREATEVIEW:

                webColumns();

                top.add(allePlaylistenView, 0,0);
                top.add(createPlaylist,2,0);
                bottom = mainViewController.getView();
                all.getChildren().addAll(top, region, bottom);
                currentDesktop = Views.CREATEVIEW;
                currentMobile = Views.CREATEVIEW;
                break;

            case SONGINFODESKTOP:

                    webColumns();
                    top.add(allePlaylistenView, 0,0);
                    top.add(songInfo, 2, 0);
                    bottom = mainViewController.getView();
                    all.getChildren().addAll(top,region, bottom);
                    songInfoControl.open();
                    songInfoControl.animate();
                    currentDesktop = Views.SONGINFODESKTOP;
                    currentMobile = Views.SONGINFOMOBILE;




        }





    }

    private void webColumns(){
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(20);
        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setPercentWidth(5);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(75);
        top.getColumnConstraints().addAll(leftColumn,centerColumn,rightColumn);
    }

    private void mobileColumns(){

        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(100);
        top.getColumnConstraints().addAll(rightColumn);

    }


    public void changePlayButton(){
        mainViewController.changePlayButton();
    }

    public ActPlaylistView getAktPlaylistViewWeb() {
        return aktPlaylistViewWeb;
    }
}
