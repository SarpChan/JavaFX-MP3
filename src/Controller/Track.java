package Controller;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import javafx.scene.image.Image;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Track {

    private String path, title="Unbekannt", album="Unbekannt", artist="Unbekannt";
    private String spotId;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    private byte [] image;
    private long songlength=0;
    private Mp3File file;
    private AudioFeatures feats;


    /** Constructor
     */
    public Track(String path){
        this.path = path;
        loadId3Tags(path);
    }

    /**
     * Getter - Spotify-ID des Liedes
     * @return Spotify-Id
     */
    public String getSpotId() {
        return spotId;
    }

    /**
     * Läd die Meta-Daten aus der MP3-Datei des Tracks.
     */

    private void loadId3Tags(String path){
        try {
            file = new Mp3File(path);

            if(file!= null) {
                if (file.hasId3v2Tag()) {
                    if (file.getId3v2Tag().getTitle() != null){

                        this.title = file.getId3v2Tag().getTitle().split("[\\(\\[]")[0];

                        this.album = file.getId3v2Tag().getAlbum();

                        this.artist = file.getId3v2Tag().getAlbumArtist();

                        if (file.getId3v2Tag().getAlbumImage() != null) {

                            image = file.getId3v2Tag().getAlbumImage();
                        }
                        this.songlength = file.getLengthInMilliseconds();

                    } else if (file.getId3v2Tag().getTitle() == null){
                        title = path.split("/")[path.split("/").length -1].split("\\.")[0];
                        this.songlength = file.getLengthInMilliseconds();
                    }

                } else if (file.hasId3v1Tag()) {
                    if (file.getId3v1Tag().getTitle() != null) {
                        this.album = file.getId3v1Tag().getAlbum();

                        this.artist = file.getId3v1Tag().getArtist();

                        this.songlength = file.getLengthInMilliseconds();
                    } else {
                        title = path.split("/")[path.split("/").length -1].split("\\.")[0];
                        this.songlength = file.getLengthInMilliseconds();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }finally{
            file = null;
        }

    }

    /**
     * Getter - Länge des Tracks.
     * @return Länge des Tracks
     */
    public long getSonglength() {
        return songlength;
    }

    /**
     * Getter - Titel des Tracks.
     * @return Titel des Tracks
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter - Zugehöriges Album des Tracks.
     * @return Zugehöriges Album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Getter - Zugehöriger Kuenstler des Tracks.
     * @return Zugehöriges Kuenstler
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Getter - Dateipfad des Tracks.
     * @return Dateipfad
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter - Hinterlegte Anzeigegrafik des Tracks.
     * @return Hinterlegte Anzeigegrafik oder Default Bild
     */
    public Image getImage() {
        if (image != null){
            return new Image(new ByteArrayInputStream(image));
        }
        else {
            return new Image("defaultCover.png");
        }

    }

    /**
     * Setter - AudioFeatures des Tracks werden zugewiesen.
     */

    public void setAudioFeatures(AudioFeatures feats){
        this.feats = feats;
    }


    /**
     * Getter - BMP Werte des Tracks.
     * @return BMP Werte
     */

    public float getBPM(){
        return feats.getTempo();
    }

    /**
     * Getter - Valence Werte des Tracks.
     * @return Valence Werte
     */
    public float getValence(){
        return feats.getValence();
    }

    /**
     * Getter - Akustische Werte des Tracks.
     * @return Akustische Werte
     */
    public float getAcousticness(){
        return feats.getAcousticness();
    }

    /**
     * Getter - Tanzbarkeit Werte des Tracks.
     * @return Tanzbarkeit Werte
     */
    public float getDanceability(){
        return feats.getDanceability();
    }

    /**
     * Getter - Energie Werte des Tracks.
     * @return Energie Werte
     */
    public float getEnergy(){
        return feats.getEnergy();
    }

    /**
     * Getter - Instrumentalness Werte des Tracks.
     * @return Instrumentalness Werte
     */
    public float getInstrumentalness(){
        return feats.getInstrumentalness();
    }

    /**
     * Getter - Liveness Werte des Tracks.
     * @return Liveness Werte
     */
    public float getLiveness(){
        return feats.getLiveness();
    }

    /**
     * Setter - SpotifyId des Tracks.
     */
    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String toString(){

        return title + "-" + album + "-" + artist + "-" + zeitanzeige.format(songlength);
    }




}
