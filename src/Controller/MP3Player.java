package Controller;


import Exceptions.keinSongException;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class MP3Player {
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	private boolean paused = false, playing = false;
	private String aktPlaylist="Test.m3u", aktSong;



	// Mp3agic
	Mp3File mp3File;



    public int getAktZeit(){
            if (audioPlayer == null){
                return 0;
            }

        return audioPlayer.position() ;
    }
    public long getSongLength(){

    	if (mp3File!= null) {

			if (mp3File.hasId3v1Tag()) {
				return mp3File.getLengthInMilliseconds();
			} else if (mp3File.hasId3v2Tag()) {
				return mp3File.getLengthInMilliseconds();
			}
		}
        return 0;

    }

    public String getSongArtist(){
		if (mp3File!= null) {

			if (mp3File.hasId3v1Tag()) {
				if (mp3File.getId3v1Tag().getArtist() != null) {
					return mp3File.getId3v1Tag().getArtist();
				}
			} else if (mp3File.hasId3v2Tag()) {
				if (mp3File.getId3v2Tag().getArtist() != null) {
					return mp3File.getId3v1Tag().getArtist();
				}
			}
		}
        return "Keine Info";
    }

    public String getAlbum(){
		if (mp3File!= null) {
			if (mp3File.hasId3v1Tag()) {
				return mp3File.getId3v2Tag().getAlbum();
			} else if (mp3File.hasId3v2Tag()) {
				return mp3File.getId3v2Tag().getAlbum();
			}
		}
        return "Keine Info";
    }

    public String getTrack(){
		if (mp3File!= null) {
			if (mp3File.hasId3v1Tag()) {
				return mp3File.getId3v2Tag().getTitle();
			} else if (mp3File.hasId3v2Tag()) {
				return mp3File.getId3v2Tag().getTitle();
			}
		}
        return "Keine Info";
    }

    public void setMp3File(String filename){
        try {
            this.mp3File = new Mp3File(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }


    }

    public Image getAlbumImage(){


        Image img = new Image("defaultCover.png");

		if (mp3File!= null) {
			try {
				if (mp3File.getId3v2Tag().getAlbumImage() != null) {
					img = SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(mp3File.getId3v2Tag().getAlbumImage())), null);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        return img ;
    }

    //Mp3agic Ende


    public MP3Player(){
		minim = new SimpleMinim(true);
	}

	public MP3Player(String s){
	    minim = new SimpleMinim(true);
	    audioPlayer = minim.loadMP3File(s);

    }

    public boolean isPlaying() {
        return audioPlayer.isPlaying();
    }

    public void play(String filename) throws keinSongException {



	    // vllt. in Eventhandler von Pause/Play-Button einbauen
	    if (paused && !playing){
			paused = false;
			playing = true;

			audioPlayer.play();


		} else if (!paused && playing){

		} else{
			try {
				audioPlayer = minim.loadMP3File(filename);
				setMp3File(filename);
				audioPlayer.play();
				aktSong = filename;

				playing = true;
				paused = false;

			} catch (Exception e) {
				throw new keinSongException();

			}
		}
	}

	public void next() throws keinSongException{
		String zeile;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(aktPlaylist));


			while ((zeile = reader.readLine()) != null){
				if(aktSong.compareTo(zeile)==0){

					if ((zeile = reader.readLine()) != null){

						minim.stop();
						audioPlayer = minim.loadMP3File(zeile);
						audioPlayer.play();
						if (paused){
							paused = false;
						}
						aktSong = zeile;
                        setMp3File(aktSong);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void play() throws keinSongException {
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song 2ausgewählt");
		}
		audioPlayer.play();
		paused = false;
		playing = true;

	}
	
	public void pause() throws keinSongException {
		
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();
		paused = true;
		playing = false;
	}
	
	public void stop() throws keinSongException{
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();
		audioPlayer.rewind();
		paused = false;
		playing = false;
	}

	public void volume (float value) {
		audioPlayer.setGain(LinearToDecibel(value));
	}

	public void getMeta() {
		audioPlayer.getMetaData();
	}

	public void exit() {
		minim.dispose();
	}

	private float LinearToDecibel(float linear)
	{
		float db;

		if (linear != 0.0f)
			db = (float) (20.0f * Math.log10(linear));
		else
			db = -144.0f;  // effectively minus infinity

		return db;
	}
}
