package scenes.singleSong;

import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


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


import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;


public class ActPlaylistView extends Pane {
    boolean paused = true;
    VBox actPlaylist, playlistDataTitle;
    HBox playlistDataTitleImg, playlistData;

    ObservableList<Playlist> allPlaylists;
    ObservableList<Track> trackList;

    TableView table;
    TableColumn songTitleCol, interpretCol, albumCol, lengthCol;

    Text status, actPlaylistTitle, actPlaylistLength, actTrackAmmount;
    ImageView actImg;

    public ActPlaylistView(MP3Player player) {
        actPlaylist = new VBox();
        playlistDataTitleImg = new HBox();
        playlistDataTitle = new VBox();
        playlistData = new HBox();

        playlistDataTitle.setAlignment(Pos.TOP_LEFT);
        playlistData.setAlignment(Pos.TOP_LEFT);
        playlistDataTitleImg.setAlignment(Pos.TOP_LEFT);
        actPlaylist.setAlignment(Pos.TOP_LEFT);

        playlistDataTitle.setPadding(new Insets(0, 60, 00, 60));
        actPlaylist.setPadding(new Insets(60, 60, 60, 60));
        playlistData.setPadding(new Insets(0, 00, 8, 00));

        trackList = FXCollections.observableArrayList();
        allPlaylists = FXCollections.observableArrayList();
        allPlaylists.addAll(PlaylistManager.getAllPlaylists());

        for (Playlist playlist:  allPlaylists)
        {
            trackList.addAll(playlist.getTracks());
        }


        //TABELLE
        table = new TableView();
        table.setEditable(true);
        table.setMaxWidth(1000);
        table.setPadding(new Insets(30, 0, 0, 0));

        songTitleCol = new TableColumn(("Titel").toUpperCase());
        interpretCol = new TableColumn(("Interpret").toUpperCase());
        albumCol = new TableColumn(("Album").toUpperCase());
        lengthCol = new TableColumn(("Länge").toUpperCase());

        songTitleCol.setCellValueFactory(new PropertyValueFactory<Track, String>("TITLE"));
        songTitleCol.setMinWidth(50);
        songTitleCol.setPrefWidth(350);
        interpretCol.setCellValueFactory(new PropertyValueFactory<Track, String>("ARTIST"));
        interpretCol.setMinWidth(50);
        interpretCol.setPrefWidth(120);
        albumCol.setCellValueFactory(new PropertyValueFactory<Track, String>("ALBUM"));
        albumCol.setMinWidth(50);
        albumCol.setPrefWidth(120);
        lengthCol.setCellValueFactory(new PropertyValueFactory<Track, String>("LENGTH"));
        lengthCol.setMinWidth(30);
        lengthCol.setPrefWidth(50);

        table.setItems(trackList);
        table.getColumns().addAll(songTitleCol, interpretCol, albumCol, lengthCol);


        //PLAYLIST DATA
        status = new Text(("Playlist / ").toUpperCase());
        actPlaylistTitle = new Text(("Playlist Titel").toUpperCase());
        actPlaylistLength = new Text(("1h 15m"));
        actTrackAmmount = new Text(("15 Tracks - "));

        status.getStyleClass().add("basictxt");
        actTrackAmmount.getStyleClass().add("basictxt");
        actPlaylistLength.getStyleClass().add("basictxt");
        actPlaylistTitle.getStyleClass().add("playlistHeadline");

        playlistData.getChildren().addAll(status, actTrackAmmount, actPlaylistLength);
        playlistDataTitle.getChildren().addAll(playlistData, actPlaylistTitle);

        //ALL PLAYLIST INFO
        actImg = new ImageView();
        actImg.setImage(player.getAlbumImage());
        actImg.setFitWidth(150);
        actImg.setFitHeight(150);
        playlistDataTitleImg.getChildren().addAll(actImg, playlistDataTitle);

        //PLAYING PLAYLIST
        actPlaylist.getChildren().addAll(playlistDataTitleImg, table);

        this.getChildren().addAll(actPlaylist);
        this.getStylesheets().add(getClass().
                getResource("style.css").toExternalForm());
        this.getStylesheets().add(getClass().
                getResource("liststyle.css").toExternalForm());

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
