package scenes.singleSong.CreatePlaylistView;


import Applikation.PlayerGUI;
import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.singleSong.observView.ObservView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CreatePlaylistViewMobile extends ScrollPane {

    boolean paused = true;
    static VBox dataAndTitle, all;
    static HBox dataAndTitleAndImg, data, playeinstellung;
    GridPane tableHeader = new GridPane();
    final int LIST_CELL_HEIGHT = 50;
    final double OPACITY = 0.5;
    ListView<Track> trackListView;

    Label titel,artist,album,length;

    ObservableList<Playlist> allPlaylists = FXCollections.observableArrayList();;


    Text status, newPlaylistName, actPlaylistLength, actTrackAmmount;
    ImageView actImg;
    Button shuffle, repeat;
    Playlist aktPlaylist;
    ObservableList<Track> list = FXCollections.observableArrayList();
    DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");

    public CreatePlaylistViewMobile(ObservView observView, MP3Player player) {
        dataAndTitleAndImg = new HBox();
        dataAndTitle = new VBox();
        data = new HBox();
        playeinstellung = new HBox(20);
        all = new VBox();
        titel = new Label(("Titel").toUpperCase());
        artist = new Label(("Künstler").toUpperCase());

        titel.setOpacity(OPACITY);
        artist.setOpacity(OPACITY);


        dataAndTitle.setAlignment(Pos.TOP_LEFT);
        data.setAlignment(Pos.TOP_LEFT);
        dataAndTitleAndImg.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 00, 8, 00));
        dataAndTitle.setPadding(new Insets(0,30,0,10));
        data.setPrefWidth(400);
        playeinstellung.setAlignment(Pos.TOP_LEFT);
        playeinstellung.setPadding(new Insets(70, 0, 0, 0));


        allPlaylists.addAll(PlaylistManager.getPlaylistArrayList());




        newPlaylistName = new Text("DEFAULT");




        //PLAYLIST DATA
        status = new Text(("Create Playlist").toUpperCase());
        status.getStyleClass().add("basictxt");



        data.getChildren().addAll(status);
        dataAndTitle.getChildren().addAll(data, newPlaylistName);

        //ALL PLAYLIST INFO
        actImg = new ImageView();
        actImg.setImage(new Image("defaultCover.png"));
        actImg.setFitWidth(150);
        actImg.setFitHeight(150);

        /*actImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                observView.switchView();
            }
        });
        */

        dataAndTitleAndImg.getChildren().addAll(actImg, dataAndTitle);
        all.getChildren().addAll(dataAndTitleAndImg);



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


    public static ReadOnlyDoubleProperty getPlaylistWidth(){
        return all.widthProperty();
    }


}
