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

	private Playlist aktPlaylist;
	private Track aktSong;
    private Thread playThread;



	// Mp3agic
	Mp3File mp3File;


    public void setAktPlaylist (Playlist playlist){
        this.aktPlaylist = playlist;
    }
    public int getAktZeit(){
            if (audioPlayer == null){
                return 0;
            }

        return audioPlayer.position() ;
    }
    public long getSongLength(){


        return aktSong!= null? aktSong.getSonglength():0;

    }

    public String getSongArtist(){

        return aktSong!=null? aktSong.getArtist():"N.A.";
    }

    public String getAlbum(){

        return aktSong!=null? aktSong.getAlbum():"N.A.";
    }

    public String getTrack(){

        return aktSong!=null? aktSong.getTitle():"N.A.";
    }

    public void setMp3File(Track filename){
        try {
            this.mp3File = new Mp3File(filename.getPath());
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


        if (aktSong!= null) {
            return aktSong.getImage() != null ? aktSong.getImage() : img;
        }
        return img;
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

    public void play(Track track) throws keinSongException {


        audioPlayer = minim.loadMP3File(track.getPath());

        setMp3File(track);
        aktSong = track;
        play();

	}
    public void play(Track track, Playlist playlist) throws keinSongException {


        if(audioPlayer == null) {

            try {
                play(track);
                aktPlaylist = playlist;


            } catch (Exception e) {
                throw new keinSongException();

            }
        } else {
            minim.stop();
            try {
                play(track);
                aktPlaylist = playlist;


            } catch (Exception e) {
                throw new keinSongException();

            }

        }

    }

    public void play() throws keinSongException {

        if (audioPlayer == null) {

                aktSong = getFirstSongFromPlaylist(PlaylistManager.getPlaylistArrayList().get(0));
                play(aktSong);
                aktPlaylist = PlaylistManager.getPlaylistArrayList().get(0);



        }
        playThread = new Thread() {

            public void run() {
                audioPlayer.play();
                interrupt();

            }

            @Override
            public void interrupt() {
                super.interrupt();
                return;
            }


        };
        playThread.start();




    }

	public void next() throws keinSongException{

        for (Track track: aktPlaylist.getTracks()
             ) {
            if(aktSong.equals(track)){
                int temp = aktPlaylist.getTracks().indexOf(track);
                if (aktPlaylist.getTracks().get(temp+1)!= null){
                    aktSong = aktPlaylist.getTracks().get(temp+1);

                    minim.stop();
                    play(aktSong);
                    break;
                }
            }

        }

	}

	public void skip(int mseconds){
        if(isInitialized()) {

            audioPlayer.skip(mseconds);
            audioPlayer.play();
        }
    }

	public boolean previous() throws keinSongException{

        Track oldTrack = null;

        for (Track track: aktPlaylist.getTracks()
                ) {
            if(aktSong.equals(track)){

                if(oldTrack != null){
                    aktSong = oldTrack;
                    stop();
                    play(aktSong);
                    return true;
                }
                return false;

            }
            oldTrack = track;

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

    public Track getFirstSongFromPlaylist(Playlist x) {

        return x.getTracks().getFirst();
    }
}
