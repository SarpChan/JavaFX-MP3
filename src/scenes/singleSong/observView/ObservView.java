package scenes.singleSong.observView;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import scenes.singleSong.*;
import scenes.singleSong.CreatePlaylistView.CreatePlaylistView;
import scenes.singleSong.CreatePlaylistView.CreatePlaylistViewController;
import scenes.singleSong.actPlaylistView.ActPlaylistView;
import scenes.singleSong.actPlaylistView.ActPlaylistViewController;
import scenes.singleSong.actPlaylistView.ActPlaylistViewMobile;
import scenes.singleSong.allPlaylistView.AllPlaylistsView;

import javax.swing.event.ChangeEvent;
import java.util.EventListener;
import java.util.Properties;


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
    private Views current;


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



        //HINTERGRUND
        Rectangle progressBackground = new Rectangle();
        progressBackground.setId("progressBackground");
        progressBackground.setHeight(750);

        root.heightProperty().addListener((observable, oldValue, newValue) -> progressBackground.setHeight(newValue.doubleValue()));
        //progressBackground.xProperty().bind(mainViewController.getWidthProperty());


        ChangeListener<Number> listenToProgress = (observable, oldValue, newValue) -> {
            progressBackground.setWidth((newValue.doubleValue() / 100)  * observView.getWidth());
        };

        observView.widthProperty().addListener((observable, oldValue, newValue) -> {
            progressBackground.setWidth((mainViewController.getProgress().getValue() / 100)  * newValue.intValue());
        });

        mainViewController.getProgress().valueProperty().addListener(listenToProgress);



        root.getChildren().addAll(progressBackground, all);
        root.setAlignment(Pos.TOP_LEFT);

        observView.widthProperty().addListener(e -> {
            aktPlaylistController.calcDataWidth(observView.getWidth());


        });

        observView.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() <= 550){
                if(current != currentMobile) {
                    mainViewController.getProgress().valueProperty().removeListener(listenToProgress);
                    mainViewControllerMobile.getProgress().valueProperty().addListener(listenToProgress);
                    switchView(currentMobile);
                }
            }else{
                if(current != currentDesktop) {
                    mainViewControllerMobile.getProgress().valueProperty().removeListener(listenToProgress);
                    mainViewController.getProgress().valueProperty().addListener(listenToProgress);
                    switchView(currentDesktop);
                }

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
                current = Views.ACTPLAYLISTDESKTOP;

                break;

            case ACTPLAYLISTMOBILE:

                top.getChildren().addAll(aktPlaylistViewMobile);
                bottom = mainViewControllerMobile.getView();
                all.getChildren().addAll(top,region,bottom);
                currentDesktop = Views.ACTPLAYLISTDESKTOP;
                currentMobile = Views.ACTPLAYLISTMOBILE;
                current = Views.ACTPLAYLISTMOBILE;
                break;

            case CREATEVIEW:

                webColumns();

                top.add(allePlaylistenView, 0,0);
                top.add(createPlaylist,2,0);
                bottom = mainViewController.getView();
                all.getChildren().addAll(top, region, bottom);
                currentDesktop = Views.CREATEVIEW;
                currentMobile = Views.CREATEVIEW;
                current = Views.CREATEVIEW;
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
                    current = Views.SONGINFODESKTOP;
                    break;




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

    private void calculateBackgroundProgress(Slider progress, Rectangle bg) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        if(actValue == half){
            bg.setWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(width-minwidth);


        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(minwidth);
        }

    }


}
