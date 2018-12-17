package Controller;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleStringProperty;


import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class Track {

    private String path, title="N.A.", album="N.A.", artist="N.A.";
    private long songlength=0;
    private Mp3File file;
    private Image image = new Image("defaultCover.png");

    private SimpleStringProperty TITLE, ALBUM, ARTIST, LENGTH;

    public Track(String path){

        this.path = path;
        loadId3Tags(path);


    }

    private void loadId3Tags(String path){
        try {
            file = new Mp3File(path);

            if(file!= null) {
                if (file.hasId3v2Tag()) {

                    this.title = file.getId3v2Tag().getTitle();
                    this.TITLE = new SimpleStringProperty(title);
                    this.album = file.getId3v2Tag().getAlbum();
                    this.ALBUM = new SimpleStringProperty(album);
                    this.artist = file.getId3v2Tag().getAlbumArtist();
                    this.ARTIST = new SimpleStringProperty(artist);
                    if (file.getId3v2Tag().getAlbumImage() != null) {
                        this.image = SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(file.getId3v2Tag().getAlbumImage())), null);
                    }
                    this.songlength = file.getLengthInMilliseconds();
                    this.LENGTH = new SimpleStringProperty(Objects.toString(songlength));
                } else if (file.hasId3v1Tag()) {
                    this.title = file.getId3v1Tag().getTitle();
                    this.TITLE = new SimpleStringProperty(title);
                    this.album = file.getId3v1Tag().getAlbum();
                    this.ALBUM = new SimpleStringProperty(album);
                    this.artist = file.getId3v1Tag().getArtist();
                    this.ARTIST = new SimpleStringProperty(artist);
                    this.songlength = file.getLengthInMilliseconds();
                    this.LENGTH = new SimpleStringProperty(Objects.toString(songlength));
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
        return image;
    }


    public String getTITLE() {
        return TITLE.get();
    }

    public String getALBUM() {
        return ALBUM.get();
    }

    public String getARTIST() {
        return ARTIST.get();
    }

    public String getLENGTH() {
        return LENGTH.get();
    }

}
