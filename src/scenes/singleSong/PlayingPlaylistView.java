package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;
import Exceptions.keinSongException;


import controlElements.ControlButtons;
import controlElements.Progress;
import controlElements.VolumeAndTime;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.cell.ComboBoxListCell;

import javafx.scene.text.TextAlignment;
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

import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;



public class PlayingPlaylistView {
    boolean paused = true;

    public Scene buildScene(PlayerGUI gui, MP3Player player) {
        VBox playingPlaylist = new VBox();


        TableView table = new TableView();
        table.setEditable(true);
        TableColumn songTitleCol = new TableColumn("Titel");
        TableColumn interpretCol = new TableColumn("Interpret");
        TableColumn albumCol = new TableColumn("Album");
        TableColumn lengthCol = new TableColumn("Länge");

        songTitleCol.setCellValueFactory(
                new PropertyValueFactory<Track, String>("Title"));

        table.getColumns().addAll(songTitleCol, interpretCol, albumCol, lengthCol);



        ScrollPane holdsList = new ScrollPane();
        HBox allPlaylistInfo = new HBox();
        VBox playlistDataWithTitle = new VBox();
        HBox playlistData = new HBox();

        playlistDataWithTitle.setAlignment(Pos.TOP_LEFT);
        playlistData.setAlignment(Pos.TOP_LEFT);
        allPlaylistInfo.setAlignment(Pos.TOP_LEFT);
        playingPlaylist.setAlignment(Pos.TOP_LEFT);

        playlistDataWithTitle.setPadding(new Insets(0, 60, 00, 60));
        playingPlaylist.setPadding(new Insets(60, 60, 60, 60));
        playlistData.setPadding(new Insets(0, 00, 8, 00));

        //PLAYLIST DATA
        Text status = new Text(("Playlist / ").toUpperCase());
        Text actPlaylistTitle = new Text(("Playlist Titel").toUpperCase());
        Text actPlaylistLength = new Text(("1h 15m"));
        Text actTrackAmmount = new Text(("15 Tracks - "));

        status.getStyleClass().add("basictxt");
        actTrackAmmount.getStyleClass().add("basictxt");
        actPlaylistLength.getStyleClass().add("basictxt");
        actPlaylistTitle.getStyleClass().add("playlistHeadline");

        playlistData.getChildren().addAll(status, actTrackAmmount, actPlaylistLength);
        playlistDataWithTitle.getChildren().addAll(playlistData, actPlaylistTitle);

        //ALL PLAYLIST INFO
        ImageView actImg = new ImageView();
        actImg.setImage(player.getAlbumImage());
        actImg.setFitWidth(116);
        actImg.setFitHeight(110);
        allPlaylistInfo.getChildren().addAll(actImg, playlistDataWithTitle);

        //ALL THE TRACKS
        //title.setText(player.getTrack());
        //interpret.setText(player.getSongArtist());


        //PLAYING PLAYLIST
        playingPlaylist.getChildren().addAll(allPlaylistInfo, table);



        VBox playlistFrame = new VBox();
        playlistFrame.setPadding(new Insets(60, 0, 60, 30));


        Text bibliothekenTxt = new Text(("Bibliotheken").toUpperCase());
        Text playlistsTxt = new Text(("Playlists +").toUpperCase());
        bibliothekenTxt.setTextAlignment(TextAlignment.LEFT);
        playlistsTxt.setTextAlignment(TextAlignment.LEFT);
        bibliothekenTxt.getStyleClass().add("headline");
        playlistsTxt.getStyleClass().add("headline");

        ListView <Playlist> playlisten = new ListView<>();
        ObservableList<Playlist> list = FXCollections.observableArrayList();
        list.addAll(PlaylistManager.getAllPlaylists());

        ListView<Track> allTracks = new ListView<>();
        ObservableList<Track> trackList = FXCollections.observableArrayList();
        holdsList.setId("actList");

        for (Playlist playlist: list)
        {
            playlisten.getItems().add(playlist);
            trackList.addAll(playlist.getTracks());

            for (Track track: trackList
            ) {

                allTracks.getItems().addAll(track);

            }
            holdsList.setContent(allTracks);

        }



        final int LIST_CELL_HEIGHT = 50;
        playlisten.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        playlisten.prefHeightProperty().bind(Bindings.size(list).multiply(LIST_CELL_HEIGHT));
        playlisten.setMaxWidth(200);

        allTracks.setBackground(new Background(new BackgroundFill( new Color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));

        playlistFrame.getChildren().addAll(bibliothekenTxt, playlisten, playlistsTxt);



        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));

        //PANES
        VBox allControlElements = new VBox();
        HBox infoAndButtons = new HBox(8);
        HBox songInfo = new HBox();

        infoAndButtons.setPadding(new Insets(25, 0, 25, 0));
        songInfo.setPadding(new Insets(0, 0, 0, 45));

        allControlElements.setAlignment(Pos.CENTER);
        songInfo.setAlignment(Pos.CENTER_LEFT);

        infoAndButtons.setId("bot");

        //ProgressBar & Slider
        Progress progressElements = new Progress();

        //ANZEIGE DES AKTUELLEN TITELS UND DES INTERPRETS
        Text actSongTitle = new Text(("Track" + " ").toUpperCase());
        Text interpret = new Text("Arctic Monkeys");
        actSongTitle.getStyleClass().add("primarytext");
        interpret.getStyleClass().add("secondarytext");

        songInfo.getChildren().addAll(actSongTitle, interpret);

        //ControlButtons
        ControlButtons controlButtons = new ControlButtons();
        //Volume&Time
        VolumeAndTime volumeAndTime = new VolumeAndTime();

        //REGIONS
        Pane distanceLeft = new HBox();
        distanceLeft.setPrefWidth(50);
        distanceLeft.setMinWidth(50);
        HBox.setHgrow(distanceLeft, Priority.ALWAYS);

        Pane distanceRight = new HBox();
        distanceRight.setPrefWidth(50);
        distanceRight.setMinWidth(50);
        HBox.setHgrow(distanceRight, Priority.ALWAYS);

        infoAndButtons.getChildren().addAll(songInfo, distanceRight, controlButtons, distanceLeft, volumeAndTime);
        allControlElements.getChildren().addAll(progressElements, infoAndButtons);

        root.setBottom(allControlElements);
        root.setCenter(playingPlaylist);
        root.setLeft(playlistFrame);


        Scene x = new Scene(root, 1024, 750);
        x.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        x.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());
        return x;
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



}
