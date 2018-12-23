package scenes.singleSong;

import Controller.MP3Player;
import Exceptions.keinSongException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.singleSong.observView.ObservView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;

public class MainViewController{

    MP3Player player;
    MainView view;
    MainViewMobile viewMobile;
    Slider progress, volume;
    DateFormat zeitanzeige;
    Text time, songLength;
    double progressValue;
    ProgressBar progressTimeSlider, progressBarVolume;
    Line progressTimeLine;
    Label interpret, titleInfo;
    Button mute, play, previous, next;
    SelectMainView select;

    private double volumePosition = 50;

    public MainViewController(ObservView observView, MP3Player player, SelectMainView select){
        this.select = select;
        this.player = player;

        switch (select){
            case DESKTOP:
                view = new MainView(player);
                progress = view.progress;
                zeitanzeige = view.zeitanzeige;
                time = view.time;
                songLength = view.songLength;
                progressTimeSlider = view.progressTimeSlider;
                progressTimeLine = view.progressTimeLine;
                interpret = view.interpret;
                titleInfo = view.titleInfo;
                volume = view.volume;
                progressBarVolume = view.progressBarVolume;
                mute = view.mute;
                play = view.play;
                previous = view.previous;
                next = view.next;
                break;

            case MOBILE:

                viewMobile = new MainViewMobile(player);
                progress = viewMobile.progress;
                zeitanzeige = viewMobile.zeitanzeige;
                time = viewMobile.time;
                songLength = viewMobile.songLength;
                progressTimeSlider = viewMobile.progressTimeSlider;
                progressTimeLine = viewMobile.progressTimeLine;
                interpret = viewMobile.interpret;
                titleInfo = viewMobile.titleInfo;
                volume = viewMobile.volume;
                progressBarVolume = viewMobile.progressBarVolume;
                mute = viewMobile.mute;
                play = viewMobile.play;
                previous = viewMobile.previous;
                next = viewMobile.next;
                break;

            default:
                break;

        }

        initialize();

    }

    public VBox getView (){
        switch (select){
            case DESKTOP: return view;
            case MOBILE: return viewMobile;
            default: return new VBox();
        }
    }

    public void initialize(){

        KeyFrame watchTimeLine = new KeyFrame(Duration.millis(50), event -> {

            if (player.getAktZeit() <= 50) {

                songLength.setText(zeitanzeige.format(player.getSongLength()));
                if(player.isInitialized()) {
                    player.volume((float) volume.getValue() / 100);
                }

            } else if(player.getAktZeit() >= player.getSongLength() - 50){
                try {
                    player.next();
                    songLength.setText(zeitanzeige.format(player.getSongLength()));
                    player.volume((float) volume.getValue() / 100);
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }else {
                time.setText(zeitanzeige.format(player.getAktZeit()));

                if(! progress.isValueChanging()) progress.setValue(((double) player.getAktZeit() / (double) player.getSongLength()) * 100);

            }
        });



        final Timeline timeline = new Timeline(watchTimeLine);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();



        progress.addEventHandler(MouseEvent.MOUSE_PRESSED, event->{

            progressValue = progress.getValue();

        });

        progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(progress, progressTimeSlider);
        });

        progress.valueChangingProperty().addListener((observable, wasChanging, isChanging)->{

            if(!isChanging) {
                player.skip((int) ((progress.getValue() / 100 * player.getSongLength()) - progressValue / 100 * player.getSongLength()));
                if (!player.isPlayerActive()) {
                    try {
                        player.pause();
                    } catch (keinSongException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        progress.valueProperty().addListener((observable, oldvar, newvar) -> {
            if( ! progress.isValueChanging()) {
                int currentTime = player.getAktZeit();
                if(Math.abs(currentTime - (newvar.doubleValue() / 100 * player.getSongLength())) > 50){
                    player.skip((int) ((newvar.doubleValue() / 100 * player.getSongLength()) - oldvar.doubleValue() / 100 * player.getSongLength()));

                    if (!player.isPlayerActive()) {
                        try {
                            player.pause();
                        } catch (keinSongException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (newvar.intValue() == 0) {

                titleInfo.setText(player.getTrack());
                interpret.setText(player.getSongArtist() + " ");
            }
            calculatePB(progress, progressTimeSlider);
        });

        mute.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (volume.getValue() != volume.getMin()){
                volumePosition = volume.getValue();
                volume.setValue(volume.getMin());
            }
            else
                volume.setValue(volumePosition);
        });

        volume.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePBForVolume(volume, progressBarVolume);

        });

        volume.valueProperty().addListener((observable, oldvar, newvar) -> {
            if(newvar.doubleValue() == 0){
                mute.setStyle("-fx-shape:\""+ getPathFromSVG("mute2") + "\";");
            }
            else{
                mute.setStyle("-fx-shape:\""+ getPathFromSVG("mute") + "\";");
            }
            calculatePBForVolume(volume, progressBarVolume);
            player.volume((newvar.floatValue() / 100));
        });

        if(player.isPlayerActive())
            play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");

        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            try {
                if (player.isInitialized()) {
                    if (!player.isPlayerActive()) {
                        player.play();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                        interpret.setText(player.getSongArtist());
                    } else {
                        player.pause();
                        play.setStyle("-fx-shape: \"" + getPathFromSVG("play") + "\";");
                    }
                } else {
                    player.play();
                    play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                    interpret.setText(player.getSongArtist());
                }

            } catch (keinSongException e) {
                e.printStackTrace();
            }

        });

        previous.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                player.previous();
                play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            } catch (keinSongException e) {

            }
        });

        next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {


                player.next();
                play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
                interpret.setText(player.getSongArtist());
                time.setText(zeitanzeige.format(player.getSongLength()));
            } catch (keinSongException e) {
                e.printStackTrace();
            }
        });

    }

    private void calculatePB(Slider progress, ProgressBar bar1) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        bar1.setProgress(1);


        if(actValue == half){
            bar1.setMinWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(width-minwidth);
            bar1.setMaxWidth(width-minwidth);

        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(minwidth);
            bar1.setMaxWidth(minwidth);
        }



    }

    private void calculatePBForVolume(Slider progress, ProgressBar bar1) {
        double actValue = progress.getValue() - 1;
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        bar1.setProgress(1);


        if(actValue == half){
            bar1.setMinWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(width-minwidth);
            bar1.setMaxWidth(width-minwidth);

        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(minwidth);
            bar1.setMaxWidth(minwidth);
        }



    }

    protected static String getPathFromSVG(String filename){
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
    public void changePlayButton(){
        play.setStyle("-fx-shape: \"" + getPathFromSVG("pause") + "\";");
    }





}
