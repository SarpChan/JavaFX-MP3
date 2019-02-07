package scenes.singleSong;

import Controller.MP3Player;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import scenes.singleSong.observView.ObservView;
import scenes.singleSong.observView.Views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.util.stream.Collectors.toMap;
import static scenes.singleSong.MainViewController.getPathFromSVG;

public class SongInfoController {

    SongInfoView view;
    ImageView cover;
    Text title, artist, length, album;
    Canvas firstAccordCanvas, secondAccordCanvas, thirdAccordCanvas, forthAccordCanvas, fifthAccordCanvas, sixthAccordCanvas;
    Text firstAccordValue, secondAccordValue, thirdAccordValue, forthAccordValue, fifthAccordValue, sixthAccordValue;
    HBox firstRings, secondRings, thirdRings;
    LinkedList<AnimationStruct> animationStructList;
    Thread animateThread;
    HBox buttonBox;
    Color mainColor;
    Button back;
    Text backSign;
    MP3Player player;
    HBox titleBox;
    boolean isOpen = true;


    public SongInfoController(MP3Player player, ObservView observ){
        this.player = player;
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
        titleBox = view.titleBox;

        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        animationStructList = new LinkedList<>();

        animationStructList.add(new AnimationStruct(firstAccordCanvas, 0));
        animationStructList.add(new AnimationStruct(secondAccordCanvas, 0));
        animationStructList.add(new AnimationStruct(thirdAccordCanvas, 0));
        animationStructList.add(new AnimationStruct(forthAccordCanvas, 0));
        animationStructList.add(new AnimationStruct(fifthAccordCanvas, 0));
        animationStructList.add(new AnimationStruct(sixthAccordCanvas, 0));


        player.songProperty().addListener((observable, oldValue, newValue) -> {
            cover.setImage(player.getAlbumImage());
            title.setText(player.getTrack());
            artist.setText(player.getSongArtist());
            album.setText(player.getAlbum());
            length.setText(zeitanzeige.format(player.getSongLength()));


            if(newValue.getSpotId() != null) {

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

                animationStructList.get(0).setMaxValue((int) (-(valuesOfSong.get(values.get(0)) * 360)));
                firstAccordValue.setText(values.get(0).toString());

                animationStructList.get(1).setMaxValue((int) (-(valuesOfSong.get(values.get(1)) * 360)));
                secondAccordValue.setText(values.get(1).toString());

                animationStructList.get(2).setMaxValue((int) (-(valuesOfSong.get(values.get(2)) * 360)));
                thirdAccordValue.setText(values.get(2).toString());

                animationStructList.get(3).setMaxValue((int) (-(valuesOfSong.get(values.get(3)) * 360)));
                forthAccordValue.setText(values.get(3).toString());

                animationStructList.get(4).setMaxValue((int) (-(valuesOfSong.get(values.get(4)) * 360)));
                fifthAccordValue.setText(values.get(4).toString());

                animationStructList.get(5).setMaxValue((int) (-(valuesOfSong.get(values.get(5)) * 360)));
                sixthAccordValue.setText(values.get(5).toString());
            } else{

                for (AnimationStruct e: animationStructList){
                    e.setMaxValue(0);
                }
                firstAccordValue.setText("N.A.");
                secondAccordValue.setText("N.A.");
                thirdAccordValue.setText("N.A.");
                forthAccordValue.setText("N.A.");
                fifthAccordValue.setText("N.A.");
                sixthAccordValue.setText("N.A.");
            }


            StartAnimateCanvasThread();


        });

        buttonBox.setOnMouseClicked(event -> {
            observ.switchView(Views.ACTPLAYLISTDESKTOP);
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

            calcDataWidth(newValue.doubleValue());
            view.line.setEndX(newValue.doubleValue() - 15);

        });

        player.aktZeitProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        buttonBox.setOnMouseEntered(event -> {
            back.setStyle("-fx-shape: \"" + getPathFromSVG("back2") + "\"; -icon-paint: -fx-primarycolor;");
            backSign.setStyle("-fx-fill:#74CCDB; -fx-font-size:12px");
        });

        buttonBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setStyle("-fx-shape: \"" + getPathFromSVG("back2") + "\"; -icon-paint: white;");
                backSign.setStyle("-fx-fill:white; -fx-font-size:12px");
            }
        });

    }

    public void animate(){

        for (AnimationStruct e: animationStructList){
            e.setCurValue(0);
        }
        StartAnimateCanvasThread();
    }

    private void StartAnimateCanvasThread(){

        animateThread = new Thread(){
            private boolean runnable;
            @Override
            public void run() {
                runnable = true;

                player.songProperty().addListener(observable -> {
                    runnable = false;
                });



                final int ADDDEGREES = 4;



                for(int i = 0; i < 360 / ADDDEGREES && runnable && isOpen; i++) {

                    for (AnimationStruct e : animationStructList) {

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

    public void open(){
        isOpen = true;
    }

    public void close(){
        isOpen = false;
    }

    public void calcDataWidth(double x){
        title.setWrappingWidth(x-250);
        titleBox.setPrefWidth(x-250);
    }

    public VBox getView(){

        return view;
    }
}
