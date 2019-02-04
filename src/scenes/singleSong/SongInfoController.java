package scenes.singleSong;

import Controller.MP3Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class SongInfoController {

    SongInfoView view;
    ImageView cover;
    Text title, artist, length, album;
    Canvas firstAccordCanvas, secondAccordCanvas, thirdAccordCanvas, forthAccordCanvas;
    Text firstAccordValue, secondAccordValue, thirdAccordValue, forthAccordValue;

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

        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");

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

            firstAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            firstAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 0, valuesOfSong.get(values.get(0)) * 360, ArcType.OPEN);
            firstAccordValue.setText(values.get(0).toString());

            secondAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            secondAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 0, valuesOfSong.get(values.get(1)) * 360, ArcType.OPEN);
            secondAccordValue.setText(values.get(1).toString());

            thirdAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            thirdAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 0, valuesOfSong.get(values.get(2)) * 360, ArcType.OPEN);
            thirdAccordValue.setText(values.get(2).toString());

            forthAccordCanvas.getGraphicsContext2D().clearRect(0,0, 150, 150);
            forthAccordCanvas.getGraphicsContext2D().strokeArc(4, 4, 144, 144, 0, valuesOfSong.get(values.get(3)) * 360, ArcType.OPEN);
            forthAccordValue.setText(values.get(3).toString());

        });

    }

    public VBox getView(){
        return view;
    }
}
