package scenes.singleSong.CreatePlaylistView;

import Controller.*;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.singleSong.observView.ObservView;
import scenes.singleSong.observView.Views;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class CreatePlaylistView extends ScrollPane {
    boolean paused = true;
    static VBox dataAndTitle, all;
    static HBox dataAndTitleAndImg, data, playeinstellung;

    final double OPACITY = 0.5;


    Label titel,artist,album,length;

    ObservableList<Playlist> allPlaylists = FXCollections.observableArrayList();;


    TextField newPlaylistName, actPlaylistLength, actTrackAmmount;
    TextField [] min, max;
    Text status;
    ImageView actImg;
    Region r1, r2;
    GridPane grid = new GridPane();
    HBox [] param = new HBox[6];
    HBox playlistErstellButtons;
    RadioButton bpm, acousticness, valence, instrumentalness, danceability, energy;
    Playlist aktPlaylist;
    DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    Button create, cancel;

    public CreatePlaylistView(ObservView observView, MP3Player player) {
        dataAndTitleAndImg = new HBox();
        dataAndTitle = new VBox();
        data = new HBox();
        playeinstellung = new HBox(20);
        all = new VBox(20);
        titel = new Label(("Titel").toUpperCase());
        artist = new Label(("Künstler").toUpperCase());
        album = new Label(("Album").toUpperCase());
        length = new Label(("Länge").toUpperCase());
        titel.setOpacity(OPACITY);
        artist.setOpacity(OPACITY);
        album.setOpacity(OPACITY);
        length.setOpacity(OPACITY);
        r1 = new Region();
        r2 = new Region();
        bpm = new RadioButton("BPM");
        acousticness = new RadioButton("Acousticness");
        valence = new RadioButton("Valence");
        instrumentalness = new RadioButton("Instrumentalness");
        danceability = new RadioButton("Danceability");
        energy = new RadioButton("Energy");
        acousticness = new RadioButton("Acousticness");
        bpm.getStyleClass().add("radio-button");
        min = new TextField[6];
        max = new TextField[6];
        playlistErstellButtons = new HBox(20);

        create = new Button("Erstelle Playlist");
        create.getStyleClass().add("create");
        cancel = new Button("Abbrechen");
        cancel.getStyleClass().add("cancel");
        playlistErstellButtons.getChildren().addAll(cancel,create);
        playlistErstellButtons.setAlignment(Pos.BOTTOM_RIGHT);

        //GridPane Nodes erzeugen


        for (int i= 0;i<min.length;i++){
            max[i] = new TextField();
            max[i].getStyleClass().add("minmax");

            max[i].setDisable(true);
            min[i] = new TextField();
            min[i].getStyleClass().add("minmax");

            min[i].setDisable(true);
            param[i] = new HBox();
            param[i].setStyle("-fx-alignment: center-left;");



        }


        bpm.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(bpm.isSelected()){

                min[0].setDisable(false);
                max[0].setDisable(false);
            } else {
                min[0].setDisable(true);

                max[0].setDisable(true);
                min[0].clear();
                max[0].clear();
            }


        });

        valence.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(valence.isSelected()){

                min[1].setDisable(false);
                max[1].setDisable(false);
            } else {
                min[1].setDisable(true);
                max[1].setDisable(true);
                min[1].clear();
                max[1].clear();
            }


        });
        energy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(energy.isSelected()){
                min[2].setDisable(false);
                max[2].setDisable(false);
            } else {
                min[2].setDisable(true);
                max[2].setDisable(true);
                min[2].clear();
                max[2].clear();
            }


        });
        danceability.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(danceability.isSelected()){
                min[3].setDisable(false);
                max[3].setDisable(false);
            } else {
                min[3].setDisable(true);
                max[3].setDisable(true);
                min[3].clear();
                max[3].clear();
            }


        });
        instrumentalness.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(instrumentalness.isSelected()){
                min[4].setDisable(false);
                max[4].setDisable(false);
            } else {
                min[4].setDisable(true);
                max[4].setDisable(true);
                min[4].clear();
                max[4].clear();
            }


        });
        acousticness.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(acousticness.isSelected()){
                min[5].setDisable(false);
                max[5].setDisable(false);
            } else {
                min[5].setDisable(true);
                max[5].setDisable(true);
                min[5].clear();
                max[5].clear();
            }


        });

        min[0].setPromptText("Min BPM");
        max[0].setPromptText("Max BPM");
        min[1].setPromptText("Min Valence");
        max[1].setPromptText("Max Valence");
        min[2].setPromptText("Min Energy");
        max[2].setPromptText("Max Energy");
        min[3].setPromptText("Min Danceability");
        max[3].setPromptText("Max Danceability");
        min[4].setPromptText("Min Instrumentalness");
        max[4].setPromptText("Max Instrumentalness");
        min[5].setPromptText("Min Acousticness");
        max[5].setPromptText("Max Acousticness");

        ColumnConstraints parameter = new ColumnConstraints();
        parameter.setPercentWidth(40);
        parameter.setHgrow(Priority.ALWAYS);
        ColumnConstraints minimal = new ColumnConstraints();
        minimal.setPercentWidth(30);
        ColumnConstraints maximal = new ColumnConstraints();
        maximal.setPercentWidth(30);
        grid.getColumnConstraints().addAll(parameter, minimal, maximal);
        RowConstraints bpmRow = new RowConstraints();
        bpmRow.setPercentHeight(16);
        RowConstraints valenceRow = new RowConstraints();
        valenceRow.setPercentHeight(16);
        RowConstraints energyRow = new RowConstraints();
        energyRow.setPercentHeight(16);
        RowConstraints danceRow = new RowConstraints();
        danceRow.setPercentHeight(16);
        RowConstraints instrumRow = new RowConstraints();
        instrumRow.setPercentHeight(16);
        RowConstraints acoustRow = new RowConstraints();
        acoustRow.setPercentHeight(16);
        grid.getRowConstraints().addAll(bpmRow,valenceRow,energyRow,danceRow,instrumRow,acoustRow);

        grid.addColumn(0,bpm,valence,energy,danceability,instrumentalness,acousticness);
        grid.addColumn(1,min[0],min[1],min[2],min[3],min[4],min[5]);
        grid.addColumn(2,max[0],max[1],max[2],max[3],max[4],max[5]);

        grid.setPrefHeight(300);


        // GridPane Ende

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            float [] array = new float[12];
           for(int i= 0; i<=5;i++){
               min[i].commitValue();
               max[i].commitValue();
               try{
                   array[i+i]= !min[i].isDisabled() && !min[i].getText().isEmpty()?Float.valueOf(min[i].getText()):0f;
               } catch (NumberFormatException e){
                   array[i+i] = 0f;
               }
               try{
                   array[i+i+1]=!max[i].isDisabled()&& !max[i].getText().isEmpty()?Float.valueOf(max[i].getText()):0f;
               } catch (NumberFormatException e){
                   array[i+i] = 0f;
               }


           }
           PlaylistCreator.createSuggestionPlaylist(array, newPlaylistName.getText());

        });

        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (int i = 0; i < min.length; i++) {
                min[i].setDisable(true);
                observView.switchView(Views.ACTPLAYLISTDESKTOP);

            }

        });

        dataAndTitle.setAlignment(Pos.TOP_LEFT);
        data.setAlignment(Pos.TOP_LEFT);
        dataAndTitleAndImg.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 00, 8, 00));
        dataAndTitle.setPadding(new Insets(0,30,0,10));
        data.setPrefWidth(400);
        playeinstellung.setAlignment(Pos.TOP_LEFT);
        playeinstellung.setPadding(new Insets(70, 0, 0, 0));


        if(PlaylistManager.getPlaylistArrayList().isEmpty()) {
            allPlaylists.addAll(PlaylistManager.getAllPlaylists());
        }

        newPlaylistName = new TextField();
        newPlaylistName.setText("DEFAULT");
        newPlaylistName.getStyleClass().add("playlistname");



        //PLAYLIST DATA
        status = new Text();
        status.setText(("Create compiled Playlist ").toUpperCase());
        status.getStyleClass().add("basictxt");

        data.getChildren().addAll(status);
        dataAndTitle.getChildren().addAll(data, newPlaylistName, playeinstellung);

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
        all.getChildren().addAll(dataAndTitleAndImg, r1, grid, playlistErstellButtons);



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

    public void setAktPlaylist(Playlist playlist ){

        aktPlaylist = playlist;



    }

    public static ReadOnlyDoubleProperty getPlaylistWidth(){
        return all.widthProperty();
    }



    public void updatePlaylistInfo(Playlist playlist){


    }



}
