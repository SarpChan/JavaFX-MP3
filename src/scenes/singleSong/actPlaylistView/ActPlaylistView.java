package scenes.singleSong.actPlaylistView;

import Controller.MP3Player;
import Controller.Playlist;
import Controller.PlaylistManager;
import Controller.Track;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import scenes.singleSong.observView.ObservView;

public class ActPlaylistView extends ScrollPane {

    boolean paused = true;
    private static VBox dataAndTitle, all;
    private static HBox dataAndTitleAndImg, data, playeinstellung;

    private final int LIST_CELL_HEIGHT = 50;
    private final double OPACITY = 0.5;

    private ListView <Track> trackListView;
    private ObservableList<Playlist> allPlaylists = FXCollections.observableArrayList();
    private ObservableList<Track> list = FXCollections.observableArrayList();

    private GridPane tableHeader;
    private Label titel,artist,album,length;
    private Text status, actPlaylistTitle, actPlaylistLength, actTrackAmmount;
    private ImageView actImg;
    private Button shuffle, repeat;
    private Playlist actPlaylist;
    private DateFormat zeitanzeige;

    /** Constructor
     *
     */
    public ActPlaylistView(ObservView observView, MP3Player player) {
        tableHeader = new GridPane();
        dataAndTitleAndImg = new HBox();
        dataAndTitle = new VBox();
        data = new HBox();
        playeinstellung = new HBox(20);
        all = new VBox();
        trackListView = new ListView<>();
        titel = new Label(("Titel").toUpperCase());
        artist = new Label(("Künstler").toUpperCase());
        album = new Label(("Album").toUpperCase());
        length = new Label(("Länge").toUpperCase());
        zeitanzeige = new SimpleDateFormat("mm:ss");

        titel.setOpacity(OPACITY);
        artist.setOpacity(OPACITY);
        album.setOpacity(OPACITY);
        length.setOpacity(OPACITY);

        /*POSITIONIERUNG, PADDING UND STYLEZUWEISUNG DER EINZELNEN SONGINFORMATIONEN*/
        playeinstellung.setAlignment(Pos.TOP_LEFT);
        dataAndTitle.setAlignment(Pos.TOP_LEFT);
        data.setAlignment(Pos.TOP_LEFT);
        dataAndTitleAndImg.setAlignment(Pos.TOP_LEFT);

        playeinstellung.setPadding(new Insets(70, 0, 0, 0));
        dataAndTitle.setPadding(new Insets(0,30,0,10));
        dataAndTitleAndImg.setPadding(new Insets(0, 0, 20, 0));
        data.setPadding(new Insets(0, 00, 8, 00));
        data.setPrefWidth(400);

        actPlaylistTitle = new Text(actPlaylist.getName());
        actPlaylistLength = new Text(String.valueOf(zeitanzeige.format(actPlaylist.getPlaytime())) + " Minuten");
        actTrackAmmount = new Text(String.valueOf(actPlaylist.getNumberTracks()) + (" Tracks - "));
        status = new Text(("Playlist / ").toUpperCase());
        actImg = new ImageView();
        actImg.setImage(actPlaylist.getTracks().getFirst().getImage());
        actImg.setFitWidth(150);
        actImg.setFitHeight(150);

        status.getStyleClass().add("basictxt");
        actTrackAmmount.getStyleClass().add("basictxt");
        actPlaylistLength.getStyleClass().add("basictxt");
        actPlaylistTitle.getStyleClass().add("playlistHeadline");

        /*??*/
        if(PlaylistManager.getPlaylistArrayList().isEmpty()) {allPlaylists.addAll(PlaylistManager.getAllPlaylists());}
        actPlaylist = PlaylistManager.getPlaylistArrayList().get(0);

        /*ERSTELLEN UND POSITIONIERUNG DER SHUFFLE- UND REPEAT BUTTONS*/
        shuffle = new Button();
        shuffle.getStyleClass().add("notActive-button");
        shuffle.setStyle("-fx-shape: \"" + getPathFromSVG("shuffle") + "\";");
        shuffle.setPadding(new Insets(20, 100, 0, 100));

        repeat = new Button();
        repeat.getStyleClass().add("notActive-button");
        repeat.setStyle("-fx-shape: \"" + getPathFromSVG("repeat") + "\";");
        repeat.setPadding(new Insets(20, 100, 0, 100));

        /*DER HEADER DER PLAYLIST WIRD ERSTELLT UND GESETZT*/
        tableHeader.add(titel, 0,0);
        tableHeader.add(artist,1,0);
        tableHeader.add(album,2,0);
        tableHeader.add(length, 3,0);
        ColumnConstraints titleColumn = new ColumnConstraints();
        titleColumn.setPercentWidth(40);
        ColumnConstraints artistColumn = new ColumnConstraints();
        artistColumn.setPercentWidth(20);
        ColumnConstraints albumColumn = new ColumnConstraints();
        albumColumn.setPercentWidth(30);
        ColumnConstraints songlengthColumn = new ColumnConstraints();
        songlengthColumn.setPercentWidth(10);
        songlengthColumn.setHalignment(HPos.RIGHT);
        titleColumn.setHalignment(HPos.LEFT);
        tableHeader.getColumnConstraints().addAll(titleColumn, artistColumn, albumColumn, songlengthColumn);

        /*DIE PLAYLIST WIRD GELADEN UND GRAFISCH AUSGEGEBEN*/
        list.addAll(actPlaylist.getTracks());

        trackListView.setItems(list);
        trackListView.getStyleClass().add("list-view");
        trackListView.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        trackListView.prefHeightProperty().bind(Bindings.size(list).multiply(LIST_CELL_HEIGHT));
        trackListView.maxWidthProperty().bind(all.widthProperty());

        /*BOX-ZUWEISUNGEN*/

        playeinstellung.getChildren().addAll(shuffle, repeat);
        data.getChildren().addAll(status, actTrackAmmount, actPlaylistLength);
        dataAndTitle.getChildren().addAll(data, actPlaylistTitle, playeinstellung);
        dataAndTitleAndImg.getChildren().addAll(actImg, dataAndTitle);
        all.getChildren().addAll(dataAndTitleAndImg, tableHeader, trackListView);

        /*actImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                observView.switchView();
            }
        });
        */

        this.setContent(all);
        this.setPadding(new Insets(0, 0, 0, 0));
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.getStyleClass().add("scrollbar");

    }

    /** Auslesen des ersten Songs in einer Playlist.
     *
     */
    private String getFirstSongFromPlaylist(String x) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(x));
            if ((line = reader.readLine()) != null){
                return line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Kein Song gefunden.";
    }

    /** Pfade der SVG-Grafiken werden vorbereitet.
     *
     */
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

    /** Methode die jeweils von Play zu Pause wechselt und andersherum.
     *
     */
    private void changePause(){
        paused = !paused;
    }

    /** Eine ausgewählte Playlist wird als die aktuelle Playlist gesetzt.
     *
     */
    public void setActPlaylist(Playlist playlist ){
        list.removeAll(actPlaylist.getTracks());
        list.addAll(playlist.getTracks());
        actPlaylist = playlist;
        trackListView.setItems(list);
    }

    /** Getter für die PlaylistWidth-Property.
     *
     */
    public static ReadOnlyDoubleProperty getPlaylistWidth(){
        return all.widthProperty();
    }

    /** Die Playlistinformationen werden mit den Informationen einer neu ausgewählten Playlist gefüllt.
     *
     */
    public void updatePlaylistInfo(Playlist playlist){
        actPlaylistTitle.setText(playlist.getName());
        actPlaylistLength.setText(String.valueOf(zeitanzeige.format(playlist.getPlaytime())) + " Minuten");
        actTrackAmmount.setText(String.valueOf(playlist.getNumberTracks()) + (" Tracks - "));
        actImg.setImage(playlist.getTracks().getFirst().getImage());
    }



}
