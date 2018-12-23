package scenes.singleSong.actPlaylistView;

import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import Exceptions.keinSongException;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


import scenes.singleSong.observView.ObservView;


public class ActPlaylistView extends ScrollPane {
    boolean paused = true;
    static VBox dataAndTitle, all;
    static HBox dataAndTitleAndImg, data, playeinstellung;
    GridPane tableHeader = new GridPane();
    final int LIST_CELL_HEIGHT = 50;
    final double OPACITY = 0.5;
    ListView <Track> trackListView;

    Label titel,artist,album,length;

    ObservableList<Playlist> allPlaylists = FXCollections.observableArrayList();;


    Text status, actPlaylistTitle, actPlaylistLength, actTrackAmmount;
    ImageView actImg;
    Button shuffle, repeat;
    private Playlist aktPlaylist;
    private ObservableList<Track> list = FXCollections.observableArrayList();
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");

    public ActPlaylistView(ObservView observView, MP3Player player) {
        dataAndTitleAndImg = new HBox();
        dataAndTitle = new VBox();
        data = new HBox();
        playeinstellung = new HBox(20);
        all = new VBox();
        titel = new Label(("Titel").toUpperCase());
        artist = new Label(("Künstler").toUpperCase());
        album = new Label(("Album").toUpperCase());
        length = new Label(("Länge").toUpperCase());
        titel.setOpacity(OPACITY);
        artist.setOpacity(OPACITY);
        album.setOpacity(OPACITY);
        length.setOpacity(OPACITY);

        dataAndTitle.setAlignment(Pos.TOP_LEFT);
        data.setAlignment(Pos.TOP_LEFT);
        dataAndTitleAndImg.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 00, 8, 00));
        dataAndTitle.setPadding(new Insets(0,30,0,10));
        data.setPrefWidth(400);
        playeinstellung.setAlignment(Pos.TOP_LEFT);
        playeinstellung.setPadding(new Insets(70, 0, 0, 0));


        allPlaylists.addAll(PlaylistManager.getAllPlaylists());
       aktPlaylist = PlaylistManager.getPlaylistArrayList().get(0);



        actPlaylistTitle = new Text(aktPlaylist.getName());
        actPlaylistLength = new Text(String.valueOf(zeitanzeige.format(aktPlaylist.getPlaytime())) + " Minuten");
        actTrackAmmount = new Text(String.valueOf(aktPlaylist.getNumberTracks()) + (" Tracks - "));

        shuffle = new Button();
        shuffle.getStyleClass().add("notActive-button");
        shuffle.setStyle("-fx-shape: \"" + getPathFromSVG("shuffle") + "\";");
        shuffle.setPadding(new Insets(20, 100, 0, 100));
        shuffle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.changeShuffle();
            if(shuffle.getStyleClass().contains("notActive-button")){

                    shuffle.getStyleClass().removeAll("notActive-button");
                    shuffle.getStyleClass().add("active-button");
            } else{
                shuffle.getStyleClass().removeAll("active-button");
                shuffle.getStyleClass().add("notActive-button");
            }

        });

        repeat = new Button();
        repeat.getStyleClass().add("notActive-button");
        repeat.setStyle("-fx-shape: \"" + getPathFromSVG("repeat") + "\";");
        repeat.setPadding(new Insets(20, 100, 0, 100));
        repeat.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.changeRepeat();
            if(repeat.getStyleClass().contains("notActive-button")){

                repeat.getStyleClass().removeAll("notActive-button");
                repeat.getStyleClass().add("active-button");
            } else{
                repeat.getStyleClass().removeAll("active-button");
                repeat.getStyleClass().add("notActive-button");
            }
        });
        //TABELLE
        tableHeader.add(titel, 0,0);
        tableHeader.add(artist,1,0);
        tableHeader.add(album,2,0);
        tableHeader.add(length, 3,0);

        ColumnConstraints titleColumn = new ColumnConstraints();
        titleColumn.setPercentWidth(50);
        ColumnConstraints artistColumn = new ColumnConstraints();
        artistColumn.setPercentWidth(20);
        ColumnConstraints albumColumn = new ColumnConstraints();
        albumColumn.setPercentWidth(20);
        ColumnConstraints songlengthColumn = new ColumnConstraints();
        songlengthColumn.setPercentWidth(10);
        songlengthColumn.setHalignment(HPos.RIGHT);
        tableHeader.getColumnConstraints().addAll(titleColumn, artistColumn, albumColumn, songlengthColumn);

        trackListView = new ListView<>();
        trackListView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {

            @Override
            public ListCell<Track> call(ListView<Track> param) {

                return new TrackCell();
            }

        });

        // Hier muss von der PlaylistListe die darzustellende Playlist geholt werden
        list.addAll(aktPlaylist.getTracks());
        trackListView.prefHeightProperty().bind(Bindings.size(list).multiply(LIST_CELL_HEIGHT));
        trackListView.setItems(list);
        trackListView.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        trackListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.play(trackListView.getSelectionModel().getSelectedItem(), aktPlaylist);
                observView.changePlayButton();
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });


        trackListView.maxWidthProperty().bind(all.widthProperty());

        //PLAYLIST DATA
        status = new Text(("Playlist / ").toUpperCase());
        status.getStyleClass().add("basictxt");
        actTrackAmmount.getStyleClass().add("basictxt");
        actPlaylistLength.getStyleClass().add("basictxt");
        actPlaylistTitle.getStyleClass().add("playlistHeadline");

        playeinstellung.getChildren().addAll(shuffle, repeat);
        data.getChildren().addAll(status, actTrackAmmount, actPlaylistLength);
        dataAndTitle.getChildren().addAll(data, actPlaylistTitle, playeinstellung);

        //ALL PLAYLIST INFO
        actImg = new ImageView();
        actImg.setImage(aktPlaylist.getTracks().getFirst().getImage());
        actImg.setFitWidth(150);
        actImg.setFitHeight(150);

        actImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                observView.switchView();
            }
        });

        dataAndTitleAndImg.getChildren().addAll(actImg, dataAndTitle);
        all.getChildren().addAll(dataAndTitleAndImg, tableHeader, trackListView);

        this.setPadding(new Insets(0, 0, 0, 0));
        this.setContent(all);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.getStyleClass().add("scrollbar");

    }

    private String getFirstSongFromPlaylist(String x) {
        String zeile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(x));
            if ((zeile = reader.readLine()) != null){
                return zeile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nichts gefunden";
    }

    private static String getPathFromSVG(String filename){
        String d = "abc";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder =  factory.newDocumentBuilder();
            Document doc = builder.parse("resources/icons/"+ filename+".svg");
            NodeList elemente = doc.getElementsByTagName("path");
            Element element = (Element) elemente.item(0);

            return element.getAttribute("d");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    private void changePause(){
        paused = !paused;
    }

    public void calcDataWidth(double x){
        actPlaylistTitle.setWrappingWidth((x-80)*0.45);
        data.setPrefWidth((x-80)*0.45);


    }

    public static ReadOnlyDoubleProperty getPlaylistWidth(){
        return all.widthProperty();
    }

    public void setAktPlaylist(Playlist playlist ){



        list.addAll(playlist.getTracks());
        list.removeAll(aktPlaylist.getTracks());
        aktPlaylist = playlist;

        trackListView.setItems(list);

    }

    public void updatePlaylistInfo(Playlist playlist){
        actPlaylistTitle.setText(playlist.getName());
        actPlaylistLength.setText(String.valueOf(zeitanzeige.format(playlist.getPlaytime())) + " Minuten");
        actTrackAmmount.setText(String.valueOf(playlist.getNumberTracks()) + (" Tracks - "));
        actImg.setImage(playlist.getTracks().getFirst().getImage());
    }

}
