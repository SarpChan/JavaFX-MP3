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

	private String aktPlaylist, aktSong;



	// Mp3agic
	Mp3File mp3File;


    public void setAktPlaylist (String playlist){
        this.aktPlaylist = playlist;
    }
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
		minim = new SimpleMinim();
	}

	public MP3Player(String s){
	    minim = new SimpleMinim();
	    audioPlayer = minim.loadMP3File(s);

    }

    public boolean isInitialized(){
        if(audioPlayer == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean isPlaying() {
        return audioPlayer.isPlaying();
    }

    public void play(String filename) throws keinSongException {


        audioPlayer = minim.loadMP3File(filename);

        setMp3File(filename);
        aktSong = filename;
        play();

	}
    public void play(String filename, String playlist) throws keinSongException {


        if(audioPlayer == null) {

            try {
                play(filename);
                aktPlaylist = playlist;


            } catch (Exception e) {
                throw new keinSongException();

            }
        } else {
            minim.stop();
            try {
                play(filename);
                aktPlaylist = playlist;


            } catch (Exception e) {
                throw new keinSongException();

            }

        }

    }

    public void play() throws keinSongException {
        if (audioPlayer == null) {
            try {
                String filename = getFirstSongFromPlaylist("/Users/"+ System.getProperty("user.name") +"/Music/default.m3u");
                play(filename);
                aktPlaylist = "/Users/"+ System.getProperty("user.name") +"/Music/default.m3u";


            } catch (Exception e) {
                throw new keinSongException();

            }
        }
        Thread playThread = new Thread() {

            public void run() {
                audioPlayer.play();
            }

        };
        playThread.start();



    }

	public void next() throws keinSongException{
		String nextRow;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(aktPlaylist));


			while ((nextRow = reader.readLine()) != null){
				if(aktSong.compareTo(nextRow)==0){

					if ((nextRow = reader.readLine()) != null){

						minim.stop();
						play(nextRow);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void skip(int mseconds){
        if(isInitialized()) {

            audioPlayer.skip(mseconds);
            audioPlayer.play();
        }
    }

	public boolean previous() throws keinSongException{
       String alteZeile = null,neueZeile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(aktPlaylist));


            while ((neueZeile = reader.readLine()) != null){
                if(aktSong.compareTo(neueZeile)==0){


                    if(alteZeile == null){
                        return false;
                    }
                        minim.stop();
                    play(alteZeile);

                        return true;

                }
                alteZeile = neueZeile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	

	
	public void pause() throws keinSongException {
		
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();

	}
	
	public void stop() throws keinSongException{
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();
		audioPlayer.rewind();

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

    public String getFirstSongFromPlaylist(String x) {
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
}
