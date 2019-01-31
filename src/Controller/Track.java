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

    private String path, title="N.A.", album="N.A.", artist="N.A.";
    private long songlength=0;
    private Mp3File file;
    private AudioFeatures feats;
    private String spotId;
    private DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
    private byte [] image;


    public Track(String path){

        this.path = path;
        loadId3Tags(path);


    }

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

    public long getSonglength() {

        return songlength;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }

    public Image getImage() {
        if (image != null){
            return new Image(new ByteArrayInputStream(image));
        }
        else {
            return new Image("defaultCover.png");
        }

    }

    public void setAudioFeatures(AudioFeatures feats){
        this.feats = feats;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String toString(){

        return title + "-" + album + "-" + artist + "-" + zeitanzeige.format(songlength);
    }




}
