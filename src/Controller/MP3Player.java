package Controller;


import Exceptions.keinSongException;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MP3Player {
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	private boolean paused = false, playing = false;
	private String aktPlaylist="Test.m3u", aktSong;


	public MP3Player(){
		minim = new SimpleMinim(true);
	}

	public MP3Player(String s){
	    minim = new SimpleMinim(true);
	    audioPlayer = minim.loadMP3File(s);

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
				audioPlayer.play();
				aktSong = filename;
				playing = true;

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
