package scenes.singleSong;

import Controller.MP3Player;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import static scenes.singleSong.MainViewController.getPathFromSVG;

public class SongInfoController {

    SongInfoView view;
    ImageView cover;
    Text title, artist, length, album;
    Canvas firstAccordCanvas, secondAccordCanvas, thirdAccordCanvas, forthAccordCanvas, fifthAccordCanvas, sixthAccordCanvas;
    Text firstAccordValue, secondAccordValue, thirdAccordValue, forthAccordValue, fifthAccordValue, sixthAccordValue;
    HBox firstRings, secondRings, thirdRings;
    LinkedList<AnmiationStruct> animationStructList;
    Thread animateThread;
    HBox buttonBox;
    Color mainColor;
    Button back;
    Text backSign;

    public SongInfoController(MP3Player player){
        view = new SongInfoView(player);
        cover = view.cover;
        title = view.title;
        artist = view.artist;
        album = view.album;
        length = view.length;
        firstAccordCanvas = view.firstAccordCanvas;
        firstAccordValue = view.firstAccordValue;
        secondAccordCanvas = view.secondAccordCanvas;
        secondAccordValue = view.secondAccordValue;
        thirdAccordCanvas = view.thirdAccordCanvas;
        thirdAccordValue = view.thirdAccordValue;
        forthAccordCanvas = view.forthAccordCanvas;
        forthAccordValue = view.forthAccordValue;
        fifthAccordCanvas = view.fifthAccordCanvas;
        fifthAccordValue = view.fifthAccordValue;
        sixthAccordCanvas = view.sixthAccordCanvas;
        sixthAccordValue = view.sixthAccordValue;

        firstRings = view.firstRings;
        secondRings = view.secondRings;
        thirdRings = view.thirdRings;

        buttonBox = view.buttonBox;
        mainColor = view.mainColor;
        back = view.back;
        backSign = view.backSign;


        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        animationStructList = new LinkedList<>();

        animationStructList.add(new AnmiationStruct(firstAccordCanvas, 0));
        animationStructList.add(new AnmiationStruct(secondAccordCanvas, 0));
        animationStructList.add(new AnmiationStruct(thirdAccordCanvas, 0));
        animationStructList.add(new AnmiationStruct(forthAccordCanvas, 0));
        animationStructList.add(new AnmiationStruct(fifthAccordCanvas, 0));
        animationStructList.add(new AnmiationStruct(sixthAccordCanvas, 0));


        player.songProperty().addListener((observable, oldValue, newValue) -> {
            cover.setImage(player.getAlbumImage());
            title.setText(player.getTrack());
            artist.setText(player.getSongArtist());
            album.setText(player.getAlbum());
            length.setText(zeitanzeige.format(player.getSongLength()));

            HashMap<SongValues, Float> valuesOfSong = new HashMap();
            valuesOfSong.put(SongValues.ACOUSTICNESS, newValue.getAcousticness());
            valuesOfSong.put(SongValues.DANCEABILITY, newValue.getDanceability());
            valuesOfSong.put(SongValues.ENERGY, newValue.getEnergy());
            valuesOfSong.put(SongValues.INSTRUMENTALNESS, newValue.getInstrumentalness());
            valuesOfSong.put(SongValues.LIVENESS, newValue.getLiveness());
            valuesOfSong.put(SongValues.VALENCE, newValue.getValence());

            Map<SongValues, Float> sortedValues = valuesOfSong.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(),(e1, e2) -> e2, LinkedHashMap::new));
            LinkedList<SongValues> values = new LinkedList<>();

            values.addAll(sortedValues.keySet());




            /*
            firstAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            firstAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(0)) * 360), ArcType.OPEN);
            */
            animationStructList.get(0).setMaxValue((int)(-(valuesOfSong.get(values.get(0)) * 360)));
            firstAccordValue.setText(values.get(0).toString());
            /*
            secondAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            secondAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(1)) * 360), ArcType.OPEN);
            */
            animationStructList.get(1).setMaxValue((int)(-(valuesOfSong.get(values.get(1)) * 360)));
            secondAccordValue.setText(values.get(1).toString());
            /*
            thirdAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            thirdAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(2)) * 360), ArcType.OPEN);
            */
            animationStructList.get(2).setMaxValue((int)(-(valuesOfSong.get(values.get(2)) * 360)));
            thirdAccordValue.setText(values.get(2).toString());
            /*
            forthAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            forthAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(3)) * 360), ArcType.OPEN);
            */
            animationStructList.get(3).setMaxValue((int)(-(valuesOfSong.get(values.get(3)) * 360)));
            forthAccordValue.setText(values.get(3).toString());
            /*
            fifthAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            fifthAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(4)) * 360), ArcType.OPEN);
            */
            animationStructList.get(4).setMaxValue((int)(-(valuesOfSong.get(values.get(4)) * 360)));
            fifthAccordValue.setText(values.get(4).toString());
            /*
            sixthAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            sixthAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 90, -(valuesOfSong.get(values.get(5)) * 360), ArcType.OPEN);
            */
            animationStructList.get(5).setMaxValue((int)(-(valuesOfSong.get(values.get(5)) * 360)));
            sixthAccordValue.setText(values.get(5).toString());



            StartAnimateCanvasThread();


        });

        view.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() <= 600){
                if (firstRings.getChildren().size() > 2 && secondRings.getChildren().size() > 2) {
                    thirdRings.getChildren().addAll(secondRings.getChildren().get(1), secondRings.getChildren().get(2));
                    secondRings.getChildren().add(0, firstRings.getChildren().get(2));
                }
            } else{
                if(thirdRings.getChildren().size() > 0){
                    firstRings.getChildren().add(secondRings.getChildren().get(0));
                    secondRings.getChildren().addAll(thirdRings.getChildren().get(0), thirdRings.getChildren().get(1));
                }
            }


        });

        player.aktZeitProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });



        buttonBox.onMouseEnteredProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            back.setStyle("-fx-fill:#74CCDB");
            backSign.setStyle("-fx-fill:#74CCDB;");

        });

    }

    private void StartAnimateCanvasThread(){

        animateThread = new Thread(){

            @Override
            public void run() {
                final int ADDDEGREES = 4;
                for(int i = 0; i < 360 / ADDDEGREES && !this.isInterrupted(); i++) {

                    for (AnmiationStruct e : animationStructList) {

                        e.addCurValue(ADDDEGREES);
                    }

                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        };

        animateThread.start();
    }

    public VBox getView(){

        return view;
    }
}
