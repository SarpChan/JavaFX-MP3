package scenes.singleSong;

import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;
import scenes.singleSong.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
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


import javafx.scene.control.Labeled;
import javafx.scene.control.Label;




public class ActPlaylistView extends ScrollPane {
    boolean paused = true;
    static VBox dataAndTitle, all;
    HBox dataAndTitleAndImg, data;

    ObservableList<Playlist> allPlaylists;
    ObservableList<Track> trackList;

    TableView table;
    TableColumn songTitleCol, interpretCol, albumCol, lengthCol;

    static Text status, actPlaylistTitle, actPlaylistLength, actTrackAmmount;
    ImageView actImg;

    public ActPlaylistView(MP3Player player) {
        dataAndTitleAndImg = new HBox();
        dataAndTitle = new VBox();
        data = new HBox();
        all = new VBox();

        dataAndTitle.setAlignment(Pos.TOP_LEFT);
        data.setAlignment(Pos.TOP_LEFT);
        dataAndTitleAndImg.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 00, 8, 00));
        dataAndTitle.setPadding(new Insets(0,30,0,30));

        trackList = FXCollections.observableArrayList();
        allPlaylists = FXCollections.observableArrayList();
        allPlaylists.addAll(PlaylistManager.getAllPlaylists());

        for (Playlist playlist:  allPlaylists)
        {
            trackList.addAll(playlist.getTracks());
            actPlaylistTitle = new Text(playlist.getName());
            actPlaylistLength = new Text(String.valueOf(playlist.getPlaytime()));
            actTrackAmmount = new Text(String.valueOf(playlist.getNumberTracks()) + (" Tracks - "));

        }

        //TABELLE
        table = new TableView();
        table.setEditable(true);
        table.setPadding(new Insets(30, 0, 0, 0));
        table.getStyleClass().add("table");

        songTitleCol = new TableColumn(("Titel").toUpperCase());
        interpretCol = new TableColumn(("Interpret").toUpperCase());
        albumCol = new TableColumn(("Album").toUpperCase());
        lengthCol = new TableColumn(("Länge").toUpperCase());

        songTitleCol.setCellValueFactory(new PropertyValueFactory<Track, String>("TITLE"));
        interpretCol.setCellValueFactory(new PropertyValueFactory<Track, String>("ARTIST"));
        albumCol.setCellValueFactory(new PropertyValueFactory<Track, String>("ALBUM"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<Track, String>("LENGTH"));

        //songTitleCol.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        //interpretCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        //albumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setItems(trackList);
        table.getColumns().addAll(songTitleCol, interpretCol, albumCol, lengthCol);


        //PLAYLIST DATA
        status = new Text(("Playlist / ").toUpperCase());
        status.getStyleClass().add("basictxt");
        actTrackAmmount.getStyleClass().add("basictxt");
        actPlaylistLength.getStyleClass().add("basictxt");
        actPlaylistTitle.getStyleClass().add("playlistHeadline");

        data.getChildren().addAll(status, actTrackAmmount, actPlaylistLength);
        dataAndTitle.getChildren().addAll(data, actPlaylistTitle);

        //ALL PLAYLIST INFO
        actImg = new ImageView();
        actImg.setImage(player.getAlbumImage());
        actImg.setFitWidth(150);
        actImg.setFitHeight(150);
        dataAndTitleAndImg.getChildren().addAll(actImg, dataAndTitle);


        all.getChildren().addAll(dataAndTitleAndImg, table);
        this.setPadding(new Insets(0, 00, 0, 00));
        this.setContent(all);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.getStyleClass().add("scrollbar");
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

    public static void calcDataWidth(double x){
        actPlaylistTitle.setWrappingWidth((x-80)*0.45);

    }

}
